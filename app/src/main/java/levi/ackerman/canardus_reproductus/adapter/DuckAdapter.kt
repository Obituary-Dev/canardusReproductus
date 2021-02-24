package levi.ackerman.canardus_reproductus.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import levi.ackerman.canardus_reproductus.*


/**
 * @author Levi Ackerman
 */
/**
 * @param layoutId: Int
 * @param duckList: List<DuckModel>
 * @param layoutId: Int
 */
class DuckAdapter(
        val context: MainActivity,
        private val duckList: List<DuckModel>,
        private val layoutId: Int) : RecyclerView.Adapter<DuckAdapter.ViewHolder>(){

    /**
     * stores all the components we need to control/set
     * when the ViewHolder is created the parameter view that we need to control is passed to it
     * AND to the master component, the android one
     * @param view: View
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val duckImage = view.findViewById<ImageView>(R.id.image_item);
        val duckName: TextView? = view.findViewById(R.id.name_item);
        val duckDesc: TextView? = view.findViewById(R.id.description_item);
        val starIcon = view.findViewById<ImageView>(R.id.star_icon);
    }

    /**
     * @param parent: ViewGroup, viewType: Int
     * gets parent as context, injects the chosen component, f.e item_horizontal_duck => layoutId
     * @return ViewHolder(view)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false);
        return ViewHolder(view);
    }
    /**
     * updates each model with the current duck
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //gets information of the indexed duck
        val currentDuck = duckList[position];

        //gets repository
        val repo = DuckRepository();

        //uses glide to get the image from the url and adds it to the component as its image
        Glide.with(context).load(Uri.parse(currentDuck.imageUrl)).into(holder.duckImage);

        //updates the name of the duck
        holder.duckName?.text = currentDuck.name;

        //updates the description of the duck
        holder.duckDesc?.text = currentDuck.desc;

        //checks whether the plant has already been liked or not
        if(currentDuck.liked){
            holder.starIcon.setImageResource(R.drawable.ic_filledstar);
        }
        else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar);

        }
        //adds interaction with the star so the duck can be liked
        holder.starIcon.setOnClickListener{
            //reverses the star state (unfilled/filled)
            currentDuck.liked = !currentDuck.liked;
            //updates the duck object
            repo.updateDuck(currentDuck);
        }

        //interaction when a plant is clicked
        holder.itemView.setOnClickListener{
            //displays the popup window
            DuckPopup(this, currentDuck).show();
        }
    }
    /**
     * returns how many items we want to dynamically display
     */
    override fun getItemCount(): Int = duckList.size;

}