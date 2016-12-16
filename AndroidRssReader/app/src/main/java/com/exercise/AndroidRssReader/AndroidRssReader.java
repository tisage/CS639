package com.exercise.AndroidRssReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AndroidRssReader extends ListActivity {

    private RSSFeed myRssFeed = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new MyTask().execute();

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL rssUrl = new URL("http://www.engadget.com/topics/podcasts/rss.xml");
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
                TextView feedTitle = (TextView) findViewById(R.id.feedtitle);
                TextView feedPubdate = (TextView) findViewById(R.id.feedpubdate);
                feedTitle.setText(myRssFeed.getTitle());
                feedPubdate.setText(myRssFeed.getPubdate());

                ArrayAdapter<RSSItem> adapter =
                        new ArrayAdapter<RSSItem>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, myRssFeed.getList());
                setListAdapter(adapter);

            } else {

                TextView textEmpty = (TextView) findViewById(android.R.id.empty);
                textEmpty.setText("No Feed Found!");
            }

            super.onPostExecute(result);
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Uri feedUri = Uri.parse(myRssFeed.getItem(position).getLink());
        Intent myIntent = new Intent(Intent.ACTION_VIEW, feedUri);
        startActivity(myIntent);
    }
}