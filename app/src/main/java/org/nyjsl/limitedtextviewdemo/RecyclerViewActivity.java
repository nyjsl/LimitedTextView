package org.nyjsl.limitedtextviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.nyjsl.limitedtextview.LimitedTextView;
import org.nyjsl.limitedtextview.interfaces.Expandable;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerview.setAdapter(new TestAdapter(getResources().getStringArray(R.array.strs)));
    }

    class TestAdapter extends RecyclerView.Adapter<TestViewHolder> implements Expandable{

        private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();

        public TestAdapter(String[] datas) {
            this.datas = datas;
        }

        private String[] datas = new String[]{};

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false));
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
            int ltvWidth = (int) Math.ceil(getScreenWidth(holder.itemView.getContext()) - dp2Px(holder.itemView.getContext(), 20));
            holder.ltv.setExpandListener(this);
            holder.ltv.setTag(position);
            final Integer expandState = mPositionsAndStates.get(position) ;
            holder.ltv.updateForRecyclerView(datas[position],ltvWidth, expandState == null? STATE_SHRINK:expandState);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        @Override
        public void onExpand(View view) {
            Object obj = view.getTag();
            if(obj != null && obj instanceof Integer){
                mPositionsAndStates.put((Integer)obj, STATE_EXPAND);
            }
        }

        @Override
        public void onShrink(View view) {
            Object obj = view.getTag();
            if(obj != null && obj instanceof Integer){
                mPositionsAndStates.put((Integer)obj, STATE_SHRINK);
            }
        }
    }


    private static class TestViewHolder extends RecyclerView.ViewHolder{

        private LimitedTextView ltv = null;

        public TestViewHolder(View itemView) {
            super(itemView);
            ltv = (LimitedTextView) itemView.findViewById(R.id.ltv);
        }
    }

    private static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return w_screen;
    }

    private static float dp2Px(Context context, float dp) {
        return context == null?-1.0F:dp * context.getResources().getDisplayMetrics().density;
    }
}
