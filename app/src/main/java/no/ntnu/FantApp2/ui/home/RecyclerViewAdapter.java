package no.ntnu.FantApp2.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.ntnu.FantApp2.Item;
import no.ntnu.FantApp2.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<Item> itemList;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.mInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String leftCell = itemList.get(position).getTitle();
        String rightCell = itemList.get(position).getSeller().getFirstName();
        String middleCell = Integer.toString(itemList.get(position).getPrice());

        holder.leftCellText.setText(leftCell);
        holder.rightCellText.setText(rightCell);
        holder.middleCellText.setText(middleCell);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView leftCellText;
        TextView rightCellText;
        TextView middleCellText;

        ViewHolder(View itemView) {
            super(itemView);
            leftCellText = itemView.findViewById(R.id.leftCell);
            rightCellText = itemView.findViewById(R.id.rightCell);
            middleCellText = itemView.findViewById(R.id.middleCell);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
