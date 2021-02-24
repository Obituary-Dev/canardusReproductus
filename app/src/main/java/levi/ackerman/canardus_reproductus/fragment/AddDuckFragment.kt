package levi.ackerman.canardus_reproductus.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import levi.ackerman.canardus_reproductus.DuckModel
import levi.ackerman.canardus_reproductus.DuckRepository
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.downloadUri
import levi.ackerman.canardus_reproductus.MainActivity
import levi.ackerman.canardus_reproductus.R
import java.util.*


/**
 * @author Levi Ackerman
 */
class AddDuckFragment(
        private val context: MainActivity
) : Fragment(){

    private var file: Uri? = null;
    private var uploadedImage: ImageView?=null;

    /**
     * creates view with inflate + corresponding layout
     * gets the uploaded image and associate it with its component
     * calls pickupImage() when button upload is clicked (so an image can be inserted)
     * calls sendForm(view) when everything is done to send it to database, and insert it in the app
     * @return view: View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_duck, container, false);

        //gets the uploadedImage to associate its component with it
        uploadedImage = view.findViewById(R.id.preview_image);

        //retrieve the button to upload the image
        val pickupImageButton = view.findViewById<Button>(R.id.add_duck_page_upload_button);
        //when clicked it opens the image folder of the phone
        pickupImageButton.setOnClickListener{ pickupImage() }
        //get the submit button
        val submitButton = view.findViewById<Button>(R.id.add_duck_page_submit_button);
        submitButton.setOnClickListener{ sendForm(view) }

        return view;
    }

    /**
     * sends the action
     */
    private fun pickupImage() {
        val intent = Intent();
        intent.type = "image/";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 47);
    }

    /**
     * @param view: View
     * assigns the ap value to the object DuckModel attributes
     * inserts it into database
     * and displays a toast to indicate it's done
     */
    private fun sendForm(view: View) {
        val repo = DuckRepository();
        repo.uploadImage(file!!){
            val duckName = view.findViewById<EditText>(R.id.add_duck_page_name_input).text.toString();
            val duckDesc = view.findViewById<EditText>(R.id.add_duck_page_desc_input).text.toString();
            val type = view.findViewById<Spinner>(R.id.add_duck_page_type_spinner_input).selectedItem.toString();
            val downloadImageUrl = downloadUri;
            val latin = view.findViewById<EditText>(R.id.add_duck_page_latin_input).text.toString();
            val length = view.findViewById<EditText>(R.id.add_duck_page_length_input).text.toString();
            val weight = view.findViewById<EditText>(R.id.add_duck_page_weight_input).text.toString();
            val egg =  view.findViewById<Spinner>(R.id.add_duck_page_egg_spinner_input).selectedItem.toString();

            //create a new plant model object
            val duck = DuckModel(
                    UUID.randomUUID().toString(),
                    duckName,
                    duckDesc,
                    downloadImageUrl.toString(),
                    type,
                    latin,
                    length,
                    weight,
                    egg
            )
            //send to database
            repo.insertDuck(duck);
            val toast: Toast = Toast.makeText(context, duckName + " has been added!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 270);
            toast.show();

        }
    }

    /**
     * receive the action
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            //checks if the data is null
            if(data == null || data.data == null) return
            //retrieves selected image
            file = data.data;
            //update the image display on the right of the button
            uploadedImage?.setImageURI(file);
        }
    }

}