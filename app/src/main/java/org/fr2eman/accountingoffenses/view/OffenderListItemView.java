package org.fr2eman.accountingoffenses.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.R;
import org.fr2eman.accountingoffenses.model.ViolatorModel;

import java.text.SimpleDateFormat;

/**
 * Created by Asus on 04.05.2016.
 */
@EViewGroup(R.layout.offender_list_item_layout)
public class OffenderListItemView extends LinearLayout {

    private static final String TAG = "fr2eman";

    @ViewById(R.id.fio_offender_item)
    TextView fioOffenderText;

    @ViewById(R.id.birth_offender_item)
    TextView birthDateText;

    private ViolatorModel model;

    public OffenderListItemView(Context context) {
        super(context);
    }

    public OffenderListItemView bind(ViolatorModel model) {
        fioOffenderText.setText(model.getSecondName() + " " +
                model.getFirstName() + " " + model.getMiddleName());
        birthDateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getDateOfBrith()));
        this.model = model;
        return this;
    }

    public ViolatorModel getModel() {
        return this.model;
    }

}
