package actionBarItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.example.sapp.vkoffline.R;

/**
 * Created by Юра on 18.10.2016.
 */

public class AdapterItemAB extends BaseAdapter {

        Context ctx;
        LayoutInflater lInflater;
        ArrayList<ItemAB> objects;
        FrameLayout selectItem;

    public AdapterItemAB(Context context, ArrayList<ItemAB> itemAB) {
            ctx = context;
            objects = itemAB;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // кол-во элементов
        @Override
        public int getCount() {
            return objects.size();
        }

        // элемент по позиции
        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        // id по позиции
        @Override
        public long getItemId(int position) {
            return position;
        }

        // пункт списка
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // используем созданные, но не используемые view
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.drawer_list_item, parent, false);
            }

            ItemAB p = getProduct(position);

            selectItem = (FrameLayout) view.findViewById(R.id.selectItem);
            selectItem.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.nameIAB)).setText(p.name);
            ((ImageView) view.findViewById(R.id.iconIAB)).setImageResource(p.icon);


            return view;
        }

        // товар по позиции
        ItemAB getProduct(int position) {
            return ((ItemAB) getItem(position));
        }


}
