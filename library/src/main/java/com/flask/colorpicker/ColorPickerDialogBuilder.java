package com.flask.colorpicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ColorPickerDialogBuilder {
	private AlertDialog.Builder builder;
	private LinearLayout pickerContainer;
	private ColorPickerView colorPickerView;
	private LightnessBar lightnessBar;
	private AlphaBar alphaBar;

	private ColorPickerDialogBuilder(Context context) {
		builder = new AlertDialog.Builder(context);
		pickerContainer = new LinearLayout(context);
		pickerContainer.setOrientation(LinearLayout.VERTICAL);
		int defaultMargin = getDimensionAsPx(context, R.dimen.default_bar_margin);

		LinearLayout.LayoutParams layoutParamsForColorPickerView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		colorPickerView = new ColorPickerView(context);
		colorPickerView.setLayoutParams(layoutParamsForColorPickerView);

		LinearLayout.LayoutParams layoutParamsForLightnessBar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDimensionAsPx(context, R.dimen.default_bar_height));
		layoutParamsForLightnessBar.setMargins(defaultMargin, defaultMargin, defaultMargin, defaultMargin);
		lightnessBar = new LightnessBar(context);
		lightnessBar.setLayoutParams(layoutParamsForLightnessBar);

		LinearLayout.LayoutParams layoutParamsForAlphaBar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDimensionAsPx(context, R.dimen.default_bar_height));
		layoutParamsForAlphaBar.setMargins(defaultMargin, 0, defaultMargin, defaultMargin);
		alphaBar = new AlphaBar(context);
		alphaBar.setLayoutParams(layoutParamsForAlphaBar);

		pickerContainer.addView(colorPickerView);
		pickerContainer.addView(lightnessBar);
		pickerContainer.addView(alphaBar);
		colorPickerView.setLightnessBar(lightnessBar);
		colorPickerView.setAlphaBar(alphaBar);
		builder.setView(pickerContainer);
	}

	public static int getDimensionAsPx(Context context, int rid) {
		return (int) (context.getResources().getDimension(rid) + .5f);
	}

	public static ColorPickerDialogBuilder with(Context context) {
		return new ColorPickerDialogBuilder(context);
	}

	public ColorPickerDialogBuilder setTitle(String title) {
		builder.setTitle(title);
		return this;
	}

	public ColorPickerDialogBuilder initialColor(int initialColor) {
		colorPickerView.setInitialColor(initialColor);
		lightnessBar.setColor(initialColor);
		alphaBar.setColor(initialColor);
		return this;
	}

	public ColorPickerDialogBuilder wheelType(ColorPickerView.WHEEL_TYPE wheelType) {
		colorPickerView.setWheelType(wheelType);
		return this;
	}

	public ColorPickerDialogBuilder density(int density) {
		colorPickerView.setDensity(density);
		return this;
	}

	public ColorPickerDialogBuilder setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
		colorPickerView.setOnColorSelectedListener(onColorSelectedListener);
		return this;
	}

	public ColorPickerDialogBuilder setPositiveButton(String text, final DialogInterface.OnClickListener onClickListener) {
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int selectedColor = colorPickerView.getSelectedColor();
				onClickListener.onClick(dialog, selectedColor);
			}
		});
		return this;
	}

	public ColorPickerDialogBuilder setNegativeButton(String text, DialogInterface.OnClickListener onClickListener) {
		builder.setNegativeButton(text, onClickListener);
		return this;
	}

	public AlertDialog build() {
		return builder.create();
	}
}