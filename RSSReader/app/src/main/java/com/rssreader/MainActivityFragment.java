package com.rssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import android.os.Handler;

/**
 * Created by tianyuelite on 6/28/15.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ProgressBar progressBar;
    private ListView listView;
    private View myView;

    //
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (myView == null) {
            myView = inflater.inflate(R.layout.fragment_main, container, false);
            progressBar = (ProgressBar) myView.findViewById(R.id.progressBar);
            listView = (ListView) myView.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            startService();
        } else {
            //
            ViewGroup parent = (ViewGroup) myView.getParent();
            parent.removeView(myView);
        }
        return myView;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    // This class allows us to receive a callback result from the service once the task is finished.
    // The only thing we need to do, is to override the onReceiveResult().
    // Notice how the resultReceiver is passed to the RssService, before starting it:

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            if (items != null) {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                // prompt error diag
                Toast.makeText(getActivity(), "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);
        Uri uri = Uri.parse(item.getLink());
        // Intent service
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
