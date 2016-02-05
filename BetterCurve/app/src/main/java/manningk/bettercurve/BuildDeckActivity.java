package manningk.bettercurve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kevin on 1/19/2016.
 */
public class BuildDeckActivity extends AppCompatActivity {

    Card testCard;
    Deck testDeck;
    InterfaceComponents testInterface;
    LinearLayout srlLayoutView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_build_test_screen);
        testCard = Card.getTestCard();

        srlLayoutView = new LinearLayout(this);

        //set vertical orientation
        srlLayoutView.setOrientation(LinearLayout.VERTICAL);
        srlLayoutView.setGravity(Gravity.TOP | Gravity.LEFT);

        ScrollView srlDeckHolder = (ScrollView) findViewById(R.id.srlDeckHolder);
        srlDeckHolder.addView(srlLayoutView);

        testDeck = Deck.getDeck();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onResume() {
        super.onResume();

    }

    public void btnCancelOnClick(View view) {
        finish();
    }

    public void addRow() {
        android.widget.LinearLayout.LayoutParams txtLayoutParams =
                new LinearLayout.LayoutParams(
                        350,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        android.widget.LinearLayout.LayoutParams smallLayoutParams =
                new LinearLayout.LayoutParams(90,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT);


        // EditText txtName = (EditText) findViewById(R.id.txtName);
        EditText txtName = new EditText(this);
        txtName.setText(testCard.getM_strName());
        txtName.setLayoutParams(txtLayoutParams);
        txtName.setFocusable(false);
        txtName.setOnClickListener(new ImprovedListener(testCard) {
            public void onClick(View arg0) {
                buildCardInfo(card);
            }
        });

        //EditText txtCost = (EditText) findViewById(R.id.txtCost);
        EditText txtCost = new EditText(this);
        txtCost.setText(String.valueOf(testCard.getM_intCost()));
        txtCost.setLayoutParams(smallLayoutParams);
        txtCost.setFocusable(false);

        //EditText txtQty = (EditText) findViewById(R.id.txtQty);
        EditText txtQty = new EditText(this);
        txtQty.setText(String.valueOf(1));
        txtQty.setLayoutParams(smallLayoutParams);
        txtQty.setFocusable(false);

        //define and create a linear layout

        LinearLayout grpLayoutView = new LinearLayout(this);
        grpLayoutView.setOrientation(LinearLayout.HORIZONTAL);
        grpLayoutView.setGravity(Gravity.TOP | Gravity.LEFT);


        Button btnRaise = new Button(this);
        btnRaise.setText("+");
        btnRaise.setLayoutParams(smallLayoutParams);
        btnRaise.setOnClickListener(new ImprovedListener(txtQty) {
            public void onClick(View arg0) {
                int current = Integer.parseInt(txtQty.getText().toString());
                txtQty.setText(String.valueOf(current + 1));
            }
        });

        Button btnLower = new Button(this);
        btnLower.setText("-");
        btnLower.setLayoutParams(smallLayoutParams);
        btnLower.setOnClickListener(new ImprovedListener(txtQty, grpLayoutView) {
            public void onClick(View arg0) {
                int current = Integer.parseInt(txtQty.getText().toString());
                if (current > 1)
                    txtQty.setText(String.valueOf(current - 1));
                else {
                    srlLayoutView.removeView(grpLayoutView);

                }

            }
        });

        grpLayoutView.addView(txtName);
        grpLayoutView.addView(txtCost);
        grpLayoutView.addView(txtQty);
        grpLayoutView.addView(btnRaise);
        grpLayoutView.addView(btnLower);

        srlLayoutView.addView(grpLayoutView);
    }


    public void btnAddCardOnClick(View view) {
        addRow();
    }

    public void btnSaveDeckOnClick(View view) {
        ArrayList<View> arrAllCardData = getAllChildren(srlLayoutView);

        int j = 0;
        int intQty[] = new int[arrAllCardData.size() / 11];

        for (int i = 0; i < arrAllCardData.size(); i += 11)
        {
            EditText qty = (EditText) arrAllCardData.get(i + 6);
            intQty[j] = Integer.parseInt(qty.getText().toString());

            j++;
        }

        int testQty[] = intQty;
    }

    public void buildCardInfo(Card card) {
        String passedName = "Card Details";
        Intent intent = new Intent(this, DetailsScreenActivity.class);
        intent.putExtra(passedName, card.statsToArray()); //<-Adds info to be passed into the new activity, such as deck loading.
        startActivity(intent);
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }


}
