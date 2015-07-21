package kr.co.composer.pedometer.listener;

import android.content.ContextWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by composer on 2015-07-21.
 */
public class HistoryClickListener implements AdapterView.OnItemClickListener {
    ContextWrapper contextWrapper;

    public HistoryClickListener(ContextWrapper contextWrapper){
        this.contextWrapper = contextWrapper;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(contextWrapper, "" + view.getId(), Toast.LENGTH_SHORT).show();
    }
}
