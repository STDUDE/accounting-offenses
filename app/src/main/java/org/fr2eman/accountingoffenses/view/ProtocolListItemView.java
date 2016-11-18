package org.fr2eman.accountingoffenses.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.R;
import org.fr2eman.accountingoffenses.model.OffenseModel;

import java.text.SimpleDateFormat;

/**
 * Created by Asus on 20.04.2016.
 */
@EViewGroup(R.layout.protocol_list_item_layout)
public class ProtocolListItemView extends LinearLayout {

    private static final String TAG = "fr2eman";

    @ViewById(R.id.protocol_number_item)
    TextView protocolNumberText;

    @ViewById(R.id.protocol_date_item)
    TextView protocolDateText;

    @ViewById(R.id.protocol_place_item)
    TextView protocolPlaceText;

    private OffenseModel model;

    public ProtocolListItemView(Context context) {
        super(context);
    }

    public ProtocolListItemView bind(OffenseModel model) {
        protocolNumberText.setText("Протокол №" + model.getNumberProtocol());
        protocolDateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getDateOffense()));
        protocolPlaceText.setText(model.getPlace());
        this.model = model;
        return this;
    }

    public OffenseModel getModel() {
        return this.model;
    }
}
