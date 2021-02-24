package levi.ackerman.canardus_reproductus

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.databaseRef
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.downloadUri
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.duckList
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.storageRef
import java.util.*

/**
 * @author Levi Ackerman
 * mechanic done in order to interact with the firebase database and retrieve ducks into a duck list
 */

class DuckRepository {
    /**
     * links with storage and database
     * retrieves the duck list (composed of DuckModel)
     * contains the current image link
     * Singleton : design pattern of an already instanced class references can be made to it to get its data
     */
    object Singleton {
        //link to access bucket Firebase Storage
        private val BUCKET_URL: String = "gs://canardus-reproductus.appspot.com";

        //connects to the "ducks" ref
        val databaseRef = FirebaseDatabase.getInstance().getReference("ducks");

        //creates a list which contains our ducks
        val duckList = arrayListOf<DuckModel>();

        //connects to storage
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL);

        //contains the current image link
        var downloadUri: Uri? = null;
    }

    /**
     * updates the duck list
     * removes the previous list and gets the new one, with callback: asynchronous
     */
    fun updateData(callback: () -> Unit){
        //absorbs data from databaseRef => duck list
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //remove previous ducks
                duckList.clear();
                //retrieve the list
                for (ds in snapshot.children) {
                    //builds a duck object
                    val duck = ds.getValue(DuckModel::class.java);
                    //checks whether the duck is null or not
                    if(duck!= null){
                        //adds duck to the list
                        duckList.add(duck);
                    }
                }
                //activate callback
                callback();
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        });
    }

    /**
     * sends image to storage with a random name in the JPG format, with callback: asynchronous
     */
    fun uploadImage(file: Uri, callback: () -> Unit){
        //check if the file is null or not
        if(file != null){
            //generate a random name to the file so we don't have twice the same
            val fileName = UUID.randomUUID().toString() + ".jpg";
            val ref = storageRef.child(fileName);
            val uploadTask = ref.putFile(file);
            //begin to send the file
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{
                task ->
                //if there was any problem when the file was sent
                if(!task.isSuccessful){
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl;
            }).addOnCompleteListener { task ->
                //check if everything worked
                if(task.isSuccessful){
                    //get the image on the storage
                    downloadUri = task.result;
                    callback();
                }
            }
        }
    }

    /**
     * update a duck object in database
     */
    fun updateDuck(duck: DuckModel) = databaseRef.child(duck.id).setValue(duck);

    /**
     * insert a  new duck object in database
     */
    fun insertDuck(duck: DuckModel) = databaseRef.child(duck.id).setValue(duck);

    /**
     * deletes a duck object from database
     */
    fun deleteDuck(duck: DuckModel) = databaseRef.child(duck.id).removeValue();


}