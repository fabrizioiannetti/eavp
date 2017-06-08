/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.eavp.service.swtchart.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.eavp.service.swtchart.barcharts.BarChart;
import org.eclipse.eavp.service.swtchart.barcharts.BarSeriesData;
import org.eclipse.eavp.service.swtchart.barcharts.IBarSeriesData;
import org.eclipse.eavp.service.swtchart.barcharts.IBarSeriesSettings;
import org.eclipse.eavp.service.swtchart.core.ColorAndFormatSupport;
import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.IPrimaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Demo5Chart extends BarChart implements IChart {

	public Demo5Chart(Composite parent, int style) {
		super(parent, style);
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setOrientation(SWT.HORIZONTAL);
		chartSettings.setHorizontalSliderVisible(true);
		chartSettings.setVerticalSliderVisible(true);
		chartSettings.setUseZeroX(false);
		chartSettings.setUseZeroY(false);
		//
		IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
		primaryAxisSettingsX.setTitle("m/z");
		primaryAxisSettingsX.setDecimalFormat(ColorAndFormatSupport.decimalFormatVariable);
		primaryAxisSettingsX.setColor(ColorAndFormatSupport.COLOR_BLACK);
		//
		IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
		primaryAxisSettingsY.setTitle("Intensity");
		primaryAxisSettingsY.setDecimalFormat(ColorAndFormatSupport.decimalFormatScientific);
		primaryAxisSettingsY.setColor(ColorAndFormatSupport.COLOR_BLACK);
		//
		applySettings(chartSettings);
		/*
		 * Create series.
		 */
		List<IBarSeriesData> barSeriesDataList = new ArrayList<IBarSeriesData>();
		ISeriesData seriesData = SeriesConverter.getSeriesXY(SeriesConverter.BAR_SERIES_1);
		//
		IBarSeriesData barSeriesData = new BarSeriesData(seriesData);
		IBarSeriesSettings barSeriesSettings = barSeriesData.getBarSeriesSettings();
		barSeriesSettings.setDescription("");
		barSeriesDataList.add(barSeriesData);
		/*
		 * Set series.
		 */
		addSeriesData(barSeriesDataList);
	}
}
