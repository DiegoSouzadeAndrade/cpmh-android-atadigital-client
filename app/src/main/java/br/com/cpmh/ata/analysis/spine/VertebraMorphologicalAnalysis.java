package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;
import java.util.List;

public class VertebraMorphologicalAnalysis implements Serializable
{
	private Vertebra vertebra;
	private VertebraIntervertebralForamenAnalysis leftIntervertebralForamenAnalysis;
	private VertebraIntervertebralForamenAnalysis rightIntervertebralForamenAnalysis;
	private VertebraTransverseForamenAnalysis leftTransverseForamenAnalysis;
	private VertebraTransverseForamenAnalysis rightTransverseForamenAnalysis;
	private VertebraPedicleAnalysis leftPedicleAnalysis;
	private VertebraPedicleAnalysis rightPedicleAnalysis;
	private VertebraPavlovRatioAnalysis pavlovRatioAnalysis;
	private VertebraCompressionRatioAnalysis compressionRatioAnalysis;
	private List<VertebraOsteophyteAnalysis> osteophytesAnalyses;


	public VertebraMorphologicalAnalysis()
	{

	}

	/**
	 * Return the Vertebra
	 *
	 * @return the Vertebra
	 */
	public Vertebra getVertebra()
	{
		return vertebra;
	}

	/**
	 * Sets the Vertebra
	 *
	 * @param vertebra the Vertebra
	 */
	public void setVertebra(Vertebra vertebra)
	{
		this.vertebra = vertebra;
	}

	/**
	 * Return the Vertebra Left Intervertebral Foramen Analysis
	 *
	 * @return the Left Intervertebral Foramen Analysis
	 */
	public VertebraIntervertebralForamenAnalysis getLeftIntervertebralForamenAnalysis()
	{
		return leftIntervertebralForamenAnalysis;
	}

	/**
	 * Sets the Vertebra Left Intervertebral Foramen Analysis
	 *
	 * @param leftIntervertebralForamenAnalysis the Left Intervertebral Foramen Analysis
	 */
	public void setLeftIntervertebralForamenAnalysis(VertebraIntervertebralForamenAnalysis leftIntervertebralForamenAnalysis)
	{
		this.leftIntervertebralForamenAnalysis = leftIntervertebralForamenAnalysis;
	}

	/**
	 * Return the Vertebra Right Intervertebral Foramen Analysis
	 *
	 * @return the Right Intervertebral Foramen Analysis
	 */
	public VertebraIntervertebralForamenAnalysis getRightIntervertebralForamenAnalysis()
	{
		return rightIntervertebralForamenAnalysis;
	}

	/**
	 * Sets the Vertebra Right Intervertebral Foramen Analysis
	 *
	 * @param rightIntervertebralForamenAnalysis the Right Intervertebral Foramen Analysis
	 */
	public void setRightIntervertebralForamenAnalysis(VertebraIntervertebralForamenAnalysis rightIntervertebralForamenAnalysis)
	{
		this.rightIntervertebralForamenAnalysis = rightIntervertebralForamenAnalysis;
	}

	/**
	 * Return the Vertebra Left Transverse Foramen Analysis
	 *
	 * @return the Left Transverse Foramen Analysis
	 */
	public VertebraTransverseForamenAnalysis getLeftTransverseForamenAnalysis()
	{
		return leftTransverseForamenAnalysis;
	}

	/**
	 * Sets the Vertebra Left Transverse Foramen Analysis
	 *
	 * @param leftTransverseForamenAnalysis the Left Transverse Foramen Analysis
	 */
	public void setLeftTransverseForamenAnalysis(VertebraTransverseForamenAnalysis leftTransverseForamenAnalysis)
	{
		this.leftTransverseForamenAnalysis = leftTransverseForamenAnalysis;
	}

	/**
	 * Return the Vertebra Right Transverse Foramen Analysis
	 *
	 * @return the Right Transverse Foramen Analysis
	 */
	public VertebraTransverseForamenAnalysis getRightTransverseForamenAnalysis()
	{
		return rightTransverseForamenAnalysis;
	}

	/**
	 * Sets the Vertebra Right Transverse Foramen Analysis
	 *
	 * @param rightTransverseForamenAnalysis the Right Transverse Foramen Analysis
	 */
	public void setRightTransverseForamenAnalysis(VertebraTransverseForamenAnalysis rightTransverseForamenAnalysis)
	{
		this.rightTransverseForamenAnalysis = rightTransverseForamenAnalysis;
	}

	/**
	 * Return the Vertebra Left Pedicle Analysis
	 *
	 * @return the Left Pedicle Analysis
	 */
	public VertebraPedicleAnalysis getLeftPedicleAnalysis()
	{
		return leftPedicleAnalysis;
	}

	/**
	 * Sets the Vertebra Left Pedicle Analysis
	 *
	 * @param leftPedicleAnalysis the Left Pedicle Analysis
	 */
	public void setLeftPedicleAnalysis(VertebraPedicleAnalysis leftPedicleAnalysis)
	{
		this.leftPedicleAnalysis = leftPedicleAnalysis;
	}

	/**
	 * Return the Vertebra Right Pedicle Analysis
	 *
	 * @return the Right Pedicle Analysis
	 */
	public VertebraPedicleAnalysis getRightPedicleAnalysis()
	{
		return rightPedicleAnalysis;
	}

	/**
	 * Sets the Vertebra Pedicle Analysis
	 *
	 * @param rightPedicleAnalysis the Pedicle Analysis
	 */
	public void setRightPedicleAnalysis(VertebraPedicleAnalysis rightPedicleAnalysis)
	{
		this.rightPedicleAnalysis = rightPedicleAnalysis;
	}

	/**
	 * Return the Vertebra PavLov Ratio Analysis
	 *
	 * @return the PavLov Ratio Analysis
	 */
	public VertebraPavlovRatioAnalysis getPavlovRatioAnalysis()
	{
		return pavlovRatioAnalysis;
	}

	/**
	 * Sets the Vertebra Pav Lov Ratio Analysis
	 *
	 * @param pavlovRatioAnalysis the Pav Lov Ratio Analysis
	 */
	public void setPavlovRatioAnalysis(VertebraPavlovRatioAnalysis pavlovRatioAnalysis)
	{
		this.pavlovRatioAnalysis = pavlovRatioAnalysis;
	}

	/**
	 * Return the vertebra Compression Ratio Analysis
	 *
	 * @return the Compression Ratio Analysis
	 */
	public VertebraCompressionRatioAnalysis getCompressionRatioAnalysis()
	{
		return compressionRatioAnalysis;
	}

	/**
	 * Sets the Compression Ratio Analysis
	 *
	 * @param compressionRatioAnalysis
	 */
	public void setCompressionRatioAnalysis(VertebraCompressionRatioAnalysis compressionRatioAnalysis)
	{
		this.compressionRatioAnalysis = compressionRatioAnalysis;
	}

	/**
	 * Return the vertebra osteophytes Analysis
	 *
	 * @return the Osteophytes Analysis
	 */
	public List<VertebraOsteophyteAnalysis> getOsteophytesAnalyses()
	{
		return osteophytesAnalyses;
	}

	public void addOsteophytesAnalyses(VertebraOsteophyteAnalysis osteophytesAnalyse)
	{
		this.osteophytesAnalyses.add(osteophytesAnalyse);
	}

	public void removeOsteophytesAnalyses(VertebraOsteophyteAnalysis osteophytesAnalyse)
	{
		this.osteophytesAnalyses.remove(osteophytesAnalyse);
	}
}
