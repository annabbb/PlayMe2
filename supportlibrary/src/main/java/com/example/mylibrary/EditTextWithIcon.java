package com.example.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vladimirdanielyan.supportlibrary.R;


public class EditTextWithIcon extends RelativeLayout {

    private ImageView icon;
    private EditText editText;
    private Context context;

    public EditTextWithIcon(Context context) {
        this(context, null);
        initializeViews(context);
        setContext(context);
    }

    public void setContext(Context mContext) {
        context = mContext;
    }

    public EditTextWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EditTextWithIcon, 0, 0);
        Drawable imagePath = a.getDrawable(R.styleable.EditTextWithIcon_image);
        String hintString = a.getString(R.styleable.EditTextWithIcon_name);
        int editTextInputType = a.getInt(R.styleable.EditTextWithIcon_type, 0);

        a.recycle();

        if (imagePath != null) {
            icon.setImageDrawable(imagePath);
        }

        if (hintString != null) {
            editText.setHint(hintString);
        }

        int editTextInput = InputType.TYPE_CLASS_NUMBER;

        if (editTextInputType == 0) {
            editTextInput = InputType.TYPE_TEXT_VARIATION_PASSWORD;
        } else if (editTextInputType == 1) {
            editTextInput = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        }

        editText.setInputType(editTextInput);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.edit_text_with_icon_layout, this);

        icon = (ImageView) findViewById(R.id.user_icon);
        editText = (EditText) findViewById(R.id.user_name_edit_view);
    }

    @SuppressWarnings("unused")
    public void setIcon(Drawable drawable) {
        icon.setImageDrawable(drawable);
    }

    @SuppressWarnings("unused")
    public void setUserName(String userName) {
        editText.setText(userName);
    }

    @SuppressWarnings("unused")
    public ImageView getIcon() {
        return icon;
    }

    @SuppressWarnings("unused")
    public String getText() {
        return editText.getText().toString();
    }
}