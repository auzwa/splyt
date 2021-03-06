package com.example.android.splitr;


import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by caustin on 11/30/15.
 */
class Diner {

    public double total;
    public final EditText etName;
    private final EditText etFirstOrder;
    public final ImageButton ibAddItem;
    private final TextView tvName;
    private final TextView tvSplitBill;
    public final ArrayList<EditText> orderList;

    public Diner(EditText et1, EditText et2, ImageButton ib, TextView tv1, TextView tv2) {
        total = 0.0;
        orderList = new ArrayList<>();
        etName = et1;
        etFirstOrder = et2;
        ibAddItem = ib;
        tvName = tv1;
        tvSplitBill = tv2;
        orderList.add(etFirstOrder);

    }



    public void setName() {

        tvName.setText(etName.getText().toString());
    }

    public void newOrder(EditText newOrder) {
        orderList.add(newOrder);
    }

    public void updateTotal (EditText toBeUpdated, double tip){
        total = 0.0;
        toBeUpdated.setText("$" + String.format("%,.2f", editTextToDouble(toBeUpdated)));
        for (int i = 0; i < orderList.size(); i++) {
            total += editTextToDouble(orderList.get(i));
        }
        tvSplitBill.setText("$" + String.format("%,.2f", total * (1 + tip)));

    }





    private double editTextToDouble(EditText et) {
        double db;
        db = Double.parseDouble(et.getText().toString().replace("$", ""));
        return db;
    }

    public void setTotalText(double tip, double tax) {
        tvSplitBill.setText("$" + String.format("%,.2f", total * (1 + tip) + total * tax));
    }

}
