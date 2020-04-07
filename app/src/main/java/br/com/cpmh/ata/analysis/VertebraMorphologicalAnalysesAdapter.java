package br.com.cpmh.ata.analysis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.spine.VertebraMorphologicalAnalysis;

public class VertebraMorphologicalAnalysesAdapter extends RecyclerView.Adapter<VertebraMorphologicalAnalysesAdapter.ViewHolder>
{
    private List<VertebraMorphologicalAnalysis> vertebraMorphologicalAnalyses;

    private List<Integer> examCount;

    private RequestOptions requestOptions = new RequestOptions();

    public VertebraMorphologicalAnalysesAdapter() {
    }

    public VertebraMorphologicalAnalysesAdapter(List<VertebraMorphologicalAnalysis> vertebraMorphologicalAnalyses) {
        this.vertebraMorphologicalAnalyses = vertebraMorphologicalAnalyses;
        examCount =new ArrayList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_vertebras, viewGroup,false);

        return new VertebraMorphologicalAnalysesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        int countValue = position, realPosition = 0;

        for(int i = 0; i < examCount.size(); i++)
        {
            if(countValue < examCount.get(i))
            {
                countValue = examCount.get(i);
                realPosition = i;
                break;
            }
            else
                {
                    countValue -= examCount.get(i);
                }
        }

        VertebraMorphologicalAnalysis chosenvertebraMorphologicalAnalyses = vertebraMorphologicalAnalyses.get(realPosition);

        switch(getEnesimalPresentExam(realPosition ,countValue))
        {
            case 0:
                //chosenvertebraMorphologicalAnalyses.getLeftIntervertebralForamenAnalysis();
                break;
            case 1:
                //chosenvertebraMorphologicalAnalyses.getRightIntervertebralForamenAnalysis();
                break;
            case 2:
                //chosenvertebraMorphologicalAnalyses.getLeftTransverseForamenAnalysis();
                break;
            case 3:
                //chosenvertebraMorphologicalAnalyses.getRightTransverseForamenAnalysis();
                break;
            case 4:
                //chosenvertebraMorphologicalAnalyses.getLeftPedicleAnalysis();
                break;
            case 5:
                //chosenvertebraMorphologicalAnalyses.getRightPedicleAnalysis();
                break;
            case 6:
                //chosenvertebraMorphologicalAnalyses.getPavlovRatioAnalysis();
                break;
            case 7:
                //chosenvertebraMorphologicalAnalyses.getCompressionRatioAnalysis();
                break;
            case 8:
                //chosenvertebraMorphologicalAnalyses.getOsteophytesAnalyses();
                break;
        }
        //requestOptions.centerCrop();
        //if(vertebraMorphologicalAnalyses.get(position).getLeftIntervertebralForamenAnalysis().getImageURL() != null)
        {
            //Glide.with(viewHolder.itemView.getRootView().getContext()).load(vertebraMorphologicalAnalyses.get(position).getTomographyId()).apply(requestOptions).into(viewHolder.tomographyId);
        }
    }

    public int getEnesimalPresentExam(int position, int amount)
    {
        int amountOfExams = amount;

        if(vertebraMorphologicalAnalyses.get(position).getLeftIntervertebralForamenAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 0;
            }
        }

        if(vertebraMorphologicalAnalyses.get(position).getRightIntervertebralForamenAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 1;
            }
        }


        if(vertebraMorphologicalAnalyses.get(position).getLeftTransverseForamenAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 2;
            }
        }


        if(vertebraMorphologicalAnalyses.get(position).getRightTransverseForamenAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 3;
            }
        }

        if(vertebraMorphologicalAnalyses.get(position).getLeftPedicleAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 4;
            }
        }
        if(vertebraMorphologicalAnalyses.get(position).getRightPedicleAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 5;
            }
        }
        if(vertebraMorphologicalAnalyses.get(position).getPavlovRatioAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 6;
            }
        }

        if(vertebraMorphologicalAnalyses.get(position).getCompressionRatioAnalysis() != null && amountOfExams <= 0)
        {
            amountOfExams -= 1;

            if(amountOfExams == 0 )
            {
                return 7;
            }
        }

        return 8;
    }

    @Override
    public int getItemCount() {
        int amountOfExams = 0;
        examCount.clear();

        for(VertebraMorphologicalAnalysis exam :vertebraMorphologicalAnalyses)
        {
            int itemCount = 0;
            if(exam.getLeftIntervertebralForamenAnalysis() != null)
                itemCount += 1;

            if(exam.getRightIntervertebralForamenAnalysis() != null)
                itemCount += 1;

            if(exam.getLeftTransverseForamenAnalysis() != null)
                itemCount += 1;

            if(exam.getRightTransverseForamenAnalysis() != null)
                itemCount += 1;

            if(exam.getLeftPedicleAnalysis() != null)
                itemCount += 1;

            if(exam.getRightPedicleAnalysis() != null)
                itemCount += 1;

            if(exam.getPavlovRatioAnalysis() != null)
                itemCount += 1;

            if(exam.getCompressionRatioAnalysis() != null)
                itemCount += 1;

            //itemCount += exam.getOsteophytesAnalyses().size();

            examCount.add(itemCount);

            amountOfExams += itemCount;
        }

        return amountOfExams;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
