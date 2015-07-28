package kr.co.composer.pedometer.bo.pedometer;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.composer.pedometer.dao.pedometer.PedoDAO;

/**
 * Created by composer on 2015-07-09.
 */
public class PedoHistoryBO {
    PedoDAO pedoDAO;

    public PedoHistoryBO() {
        pedoDAO = new PedoDAO();
        init();
    }

    public void init() {
        pedoDAO.init();
    }

    public void insert(Pedometer pedometer) {
        pedoDAO.insert(pedometer);
    }

    public void update(Pedometer pedometer) {
        pedoDAO.update(pedometer);
    }

    public ArrayList<Pedometer> getPedometerList() {
        return pedoDAO.getPedometerList();
    }

    public ArrayList<String> getGroup() {
        return pedoDAO.getGroup();
    }

    public HashMap<String, ArrayList<Pedometer>> getChildList() {
        return pedoDAO.getChildList();
    }

    public int getWeekCount() {
        return pedoDAO.getWeekCount();
    }

    public boolean getTodayCheck() {
        return pedoDAO.getTodayCheck();
    }

    public int getTodayCount() {
        return pedoDAO.getTodayCount();
    }

    public int getMaxCount() {
        return pedoDAO.getMaxCount();
    }

}
