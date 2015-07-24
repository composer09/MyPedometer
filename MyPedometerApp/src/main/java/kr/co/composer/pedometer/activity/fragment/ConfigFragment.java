package kr.co.composer.pedometer.activity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.config.ConfigAdapter;
import kr.co.composer.pedometer.config.ConfigItem;
import kr.co.composer.pedometer.sharedpref.ConfigPreferenceManager;

public class ConfigFragment extends Fragment {
    private static final String ALERT_TITLE = "Sensitivity";
    private ConfigPreferenceManager configPref = null;
    private int SelectIndex;
    ArrayList<ConfigItem> arrList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configPref = ConfigPreferenceManager.getInstance();
        SelectIndex = configPref.getSensitivityIndex();
        arrList = new ArrayList<ConfigItem>();
        arrList.add(new ConfigItem("민감도", "만보계 센서의 민감도를 조절합니다."));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        ListView listView = (ListView) view.findViewById(R.id.config_listview);
        ConfigAdapter configAdapter = new ConfigAdapter(getActivity(), R.layout.config_item, arrList);
        listView.setAdapter(configAdapter);
        listView.setOnItemClickListener(listViewClickListener);
        return view;
    }


    AdapterView.OnItemClickListener listViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    new AlertDialog.Builder(getActivity())
                            .setTitle(ALERT_TITLE)
                            .setSingleChoiceItems(R.array.sensitivity_item, SelectIndex
                                    , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SelectIndex = which;

                                }
                            })
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setSensitivity(SelectIndex);
                                    Log.i("SelectIndex", SelectIndex + "");
//                                    String[] sensitivity = getResources()
//                                            .getStringArray(R.array.sensitivity_item);
                                }
                            })
                            .setNeutralButton("최소", null)
                            .create().show();
                    break;
            }
        }
    };


    private void setSensitivity(int which) {
        switch (which) {
            case 0:
                configPref.setSensitivityIndex(0);
                configPref.setSensitivityValue(25);
                break;
            case 1:
                configPref.setSensitivityIndex(1);
                configPref.setSensitivityValue(40);
                break;
            case 2:
                configPref.setSensitivityIndex(2);
                configPref.setSensitivityValue(60);
                break;
            case 3:
                configPref.setSensitivityIndex(3);
                configPref.setSensitivityValue(85);
                break;
            case 4:
                configPref.setSensitivityIndex(4);
                configPref.setSensitivityValue(95);
                break;
            case 5:
                configPref.setSensitivityIndex(5);
                configPref.setSensitivityValue(105);
                break;
            case 6:
                configPref.setSensitivityIndex(6);
                configPref.setSensitivityValue(125);
                break;
            case 7:
                configPref.setSensitivityIndex(7);
                configPref.setSensitivityValue(140);
                break;
            case 8:
                configPref.setSensitivityIndex(8);
                configPref.setSensitivityValue(160);
                break;
        }
    }

}
