package levi.ackerman.canardus_reproductus

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import levi.ackerman.canardus_reproductus.adapter.DuckAdapter


/**
 * @author Levi Ackerman
 */
class DuckPopup (
        private val adapter: DuckAdapter,
        private val currentDuck:DuckModel) : Dialog(adapter.context) {

    /**
     * creates the pop up window with our title and links it to the right layout (popup_duck_details.xml)
     * calls setupComponents()
     * calls setupCloseButton()
     * calls setupDeleteButton()
     * calls setupStarButton()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_duck_details);
        setupComponents();
        setupCloseButton();
        setupDeleteButton();
        setupStarButton();
    }

    /**
     * links the duck data to the pop up to make it display the right information when the duck image is clicked
     */
    private fun setupComponents() {
        //updates duck image
        val duckImage = findViewById<ImageView>(R.id.image_item);
        Glide.with(adapter.context).load(Uri.parse(currentDuck.imageUrl)).into(duckImage);
        //updates duck name
        findViewById<TextView>(R.id.pup_up_window_duck_name).text = currentDuck.name;
        //updates duck description
        findViewById<TextView>(R.id.pop_up_window_duck_description_subtitle).text = currentDuck.desc;
        //updates duck type
        findViewById<TextView>(R.id.pop_up_window_duck_type_subtitle).text = currentDuck.type;
        //updates duck latin name
        findViewById<TextView>(R.id.pop_up_window_duck_latin_subtitle).text = currentDuck.latin;
        //updates duck length
        findViewById<TextView>(R.id.pop_up_window_duck_length_subtitle).text = currentDuck.length;
        //updates duck weight
        findViewById<TextView>(R.id.pop_up_window_duck_weight_subtitle).text = currentDuck.weight;
        //updates the number of eggs
        findViewById<TextView>(R.id.pop_up_window_duck_egg_subtitle).text = currentDuck.egg;
    }


    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //closes the window
            dismiss();
        }
    }
    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            //deletes duck from database
            val repo = DuckRepository();
            repo.deleteDuck(currentDuck);
            dismiss();
        }
    }

    private fun updateStar(button: ImageView){
        if(currentDuck.liked){
            button.setImageResource(R.drawable.ic_filledstar);
        }
        else {
            button.setImageResource(R.drawable.ic_unstar);
        }
    }

    private fun setupStarButton() {
        //gets
        val starButton = findViewById<ImageView>(R.id.star_button);
        updateStar(starButton);

        //interaction with database
        starButton.setOnClickListener{
            currentDuck.liked = !currentDuck.liked;
            val repo = DuckRepository();
            repo.updateDuck(currentDuck);
            updateStar(starButton);
        }
    }
}