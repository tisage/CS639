package com.rssreader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private RSSFeed myRssFeed = null;
    private ListView listView;
    private View myView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) myView.findViewById(R.id.listView);
        // listen for click
        listView.setOnItemClickListener(this);
        new MyTask().execute();

        return myView;
    }

    // AsyncTask for loading data
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL rssUrl = new URL("http://www.engadget.com/topics/podcasts/rss.xml");
                // engadget rss feed

                SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
                SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
                XMLReader myXMLReader = mySAXParser.getXMLReader();
                RSSHandler myRSSHandler = new RSSHandler();
                myXMLReader.setContentHandler(myRSSHandler);
                InputSource myInputSource = new InputSource(rssUrl.openStream());
                myXMLReader.parse(myInputSource);

                myRssFeed = myRSSHandler.getFeed();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (myRssFeed != null) {
                TextView feedTitle = (TextView) myView.findViewById(R.id.feedtitle);
                //TextView feedPubdate = (TextView) myView.findViewById(R.id.feedpubdate);
                feedTitle.setText(myRssFeed.getTitle());
                //feedPubdate.setText(myRssFeed.getPubdate());

                ArrayAdapter<RSSItem> adapter =
                        //new ArrayAdapter<RSSItem>(getActivity().getApplicationContext(),
                        // color issue
                        new ArrayAdapter<RSSItem>(getActivity(),
                                android.R.layout.simple_list_item_1, myRssFeed.getList());
                ListView lv = (ListView) myView.findViewById(R.id.listView);
                lv.setAdapter(adapter);

            } else {
                TextView textEmpty = (TextView) myView.findViewById(android.R.id.empty);
                textEmpty.setText("No Feed Found!");
            }

            super.onPostExecute(result);
        }

    }

    // listen for click
    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        Uri feedUri = Uri.parse(myRssFeed.getItem(position).getLink());
        Intent myIntent = new Intent(Intent.ACTION_VIEW, feedUri);
        startActivity(myIntent);
    }
}