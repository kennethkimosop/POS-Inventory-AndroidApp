package com.myfirst.lesson1java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    // Declare variables at class level
    TextView qntyBread, qntyMilk, qntySugar, qntyTissue; //declared and can be accessed from anywhere
    TextView totalBread, totalMilk, totalSugar, totalTissue;
    TextView grandTotal;
    Button resetButton;

    EditText unitBread, qtyBread;
    EditText unitPen, qtyPen;
    EditText unitWatch, qtyWatch;
    EditText unitMilk, qtyMilk;

    TextView totalBreadPos, totalPenPos, totalWatchPos, totalMilkPos;
    TextView posGrandTotal, discountValue, netPayValue;

    Button btnPosBread, btnPosPen, btnPosWatch, btnPosMilk;
    Button btnGrandTotal, btnDiscount, btnNetPay;

    final int PRICE_BREAD = 50; //final integers are declared for final and cannot be declared
    final int PRICE_MILK = 40;
    final int PRICE_SUGAR = 30;
    final int PRICE_TISSUE = 60;

    int qBread = 0, qMilk = 0, qSugar = 0, qTissue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ==== Static Products Section ====
        //finds the UI element in the layout
        qntyBread = findViewById(R.id.qntyBread);
        qntyMilk = findViewById(R.id.qntyMilk);
        qntySugar = findViewById(R.id.qntySugar);
        qntyTissue = findViewById(R.id.qntyTissue);

        totalBread = findViewById(R.id.totalBread);
        totalMilk = findViewById(R.id.totalMilk);
        totalSugar = findViewById(R.id.totalSugar);
        totalTissue = findViewById(R.id.totalTissue);

        grandTotal = findViewById(R.id.grandTotal);
        resetButton = findViewById(R.id.resetButton);

//when the button is clicked the cd
        findViewById(R.id.btnBread).setOnClickListener(v -> {
            qBread++; //each time the button is clicked
            updateItem(qBread, PRICE_BREAD, qntyBread, totalBread);
        });

        findViewById(R.id.btnMilk).setOnClickListener(v -> {
            qMilk++;
            updateItem(qMilk, PRICE_MILK, qntyMilk, totalMilk);
        });

        findViewById(R.id.btnSugar).setOnClickListener(v -> {
            qSugar++;
            updateItem(qSugar, PRICE_SUGAR, qntySugar, totalSugar);
        });

        findViewById(R.id.btnTissue).setOnClickListener(v -> {
            qTissue++;
            updateItem(qTissue, PRICE_TISSUE, qntyTissue, totalTissue);
        });

        resetButton.setOnClickListener(v -> {
            qBread = qMilk = qSugar = qTissue = 0;

            qntyBread.setText("0");
            qntyMilk.setText("0");
            qntySugar.setText("0");
            qntyTissue.setText("0");

            totalBread.setText("0");
            totalMilk.setText("0");
            totalSugar.setText("0");
            totalTissue.setText("0");

            grandTotal.setText("0");
        });

        // ==== Point-of-Sale Section ====
        unitBread = findViewById(R.id.unitBread);
        qtyBread = findViewById(R.id.qtyBread);
        totalBreadPos = findViewById(R.id.totalBreadPos);

        unitPen = findViewById(R.id.unitPen);
        qtyPen = findViewById(R.id.qtyPen);
        totalPenPos = findViewById(R.id.totalPenPos);

        unitWatch = findViewById(R.id.unitWatch);
        qtyWatch = findViewById(R.id.qtyWatch);
        totalWatchPos = findViewById(R.id.totalWatchPos);

        unitMilk = findViewById(R.id.unitMilk);
        qtyMilk = findViewById(R.id.qtyMilk);
        totalMilkPos = findViewById(R.id.totalMilkPos);

        posGrandTotal = findViewById(R.id.posGrandTotal);
        discountValue = findViewById(R.id.discountValue);
        netPayValue = findViewById(R.id.netPayValue);

        btnPosBread = findViewById(R.id.btnPosBread);
        btnPosPen = findViewById(R.id.btnPosPen);
        btnPosWatch = findViewById(R.id.btnPosWatch);
        btnPosMilk = findViewById(R.id.btnPosMilk);

        btnGrandTotal = findViewById(R.id.btnGrandTotal);
        btnDiscount = findViewById(R.id.btnDiscount);
        btnNetPay = findViewById(R.id.btnNetPay);

        btnPosBread.setOnClickListener(v -> calculateItemTotal(unitBread, qtyBread, totalBreadPos));
        btnPosPen.setOnClickListener(v -> calculateItemTotal(unitPen, qtyPen, totalPenPos));
        btnPosWatch.setOnClickListener(v -> calculateItemTotal(unitWatch, qtyWatch, totalWatchPos));
        btnPosMilk.setOnClickListener(v -> calculateItemTotal(unitMilk, qtyMilk, totalMilkPos));

        btnGrandTotal.setOnClickListener(v -> {
            double bread = getDoubleFromTextView(totalBreadPos);
            double pen = getDoubleFromTextView(totalPenPos);
            double watch = getDoubleFromTextView(totalWatchPos);
            double milk = getDoubleFromTextView(totalMilkPos);
            double grand = bread + pen + watch + milk;
            posGrandTotal.setText(String.format("%.2f", grand));
        });

        btnDiscount.setOnClickListener(v -> {
            double grand = getDoubleFromTextView(posGrandTotal);
            double discount = grand * 0.15;
            discountValue.setText(String.format("%.2f", discount));
        });

        btnNetPay.setOnClickListener(v -> {
            double grand = getDoubleFromTextView(posGrandTotal);
            double discount = getDoubleFromTextView(discountValue);
            double netPay = grand - discount;
            netPayValue.setText(String.format("%.2f", netPay));
        });
    }

    // ---- Helpers ----
    private void calculateItemTotal(EditText unitField, EditText qtyField, TextView totalView) {
      try {
            double unit = Double.parseDouble(unitField.getText().toString());
            int qty = Integer.parseInt(qtyField.getText().toString());
            double total = unit * qty;
            totalView.setText(String.format("%.2f", total));
      } catch (NumberFormatException e) {
           totalView.setText("1.00");
        }
    }
    //Updating the total items for the first section
    void updateItem(int quantity, int price, TextView qntyView, TextView totalView) {
        int itemTotal = quantity * price;
        qntyView.setText(String.valueOf(quantity));
        totalView.setText(String.valueOf(itemTotal));
        updateGrandTotal();
    }

    //Updating the calculations for the second section
    void updateGrandTotal() {
        int total = (qBread * PRICE_BREAD) + (qMilk * PRICE_MILK)
                + (qSugar * PRICE_SUGAR) + (qTissue * PRICE_TISSUE);
        grandTotal.setText(String.valueOf(total));
    }
    //performing error handling
    private double getDoubleFromTextView(TextView view) {
        try {
            return Double.parseDouble(view.getText().toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}

