package br.com.cpmh.ata.analysis.spine;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import br.com.cpmh.ata.analysis.MorphologicalAnalysis;

public class SpineMorphologicalAnalysis extends MorphologicalAnalysis
{
	private BenzelPolygonAnalysis benzelPolygonAnalysis;

	private List<VertebraMorphologicalAnalysis> vertebraMorphologicalAnalysisList;

	public SpineMorphologicalAnalysis() {
	}

	public SpineMorphologicalAnalysis(Parcel in) {
		super(in);

		//this.benzelPolygonAnalysis = in.;

		this.vertebraMorphologicalAnalysisList = new ArrayList<>();
	}

	/**
	 *
	 * @return
	 */
	public BenzelPolygonAnalysis getBenzelPolygonAnalysis() {
		return benzelPolygonAnalysis;
	}

	/**
	 *
	 * @param benzelPolygonAnalysis
	 */
	public void setBenzelPolygonAnalysis(BenzelPolygonAnalysis benzelPolygonAnalysis) {
		this.benzelPolygonAnalysis = benzelPolygonAnalysis;
	}

	/**
	 *
	 * @return
	 */
	public List<VertebraMorphologicalAnalysis> getVertebraMorphologicalAnalysisList() {
		return vertebraMorphologicalAnalysisList;
	}

	/**
	 *
	 * @param vertebraMorphologicalAnalysisList
	 */
	public void setVertebraMorphologicalAnalysisList(List<VertebraMorphologicalAnalysis> vertebraMorphologicalAnalysisList) {
		this.vertebraMorphologicalAnalysisList = vertebraMorphologicalAnalysisList;
	}
}
