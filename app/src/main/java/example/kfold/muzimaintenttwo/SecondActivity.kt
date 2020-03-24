package example.kfold.muzimaintenttwo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {

    lateinit var mIntentTV : TextView
    lateinit var gson : Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gson = Gson()

        mIntentTV = intentTV

        when {
            intent?.action == "example.kfold.muzimaintenttwo.ACTION_REQUEST_RESOURCE" -> {
                var resultIntent = Intent()
                var encounter = Encounter("Good", 1, "Encounter1")
                var encounterJson = gson.toJson(encounter)

                resultIntent.putExtra("resource", encounterJson)
                resultIntent.putExtra("class", "encounter")
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            intent?.action == "example.kfold.muzimaintenttwo.ACTION_PROVIDER_TO_FHIR" -> {
                var resourceData = intent.getStringExtra("resource")
                mIntentTV.setText(resourceData)
            }
            intent?.action == "example.kfold.muzimaintenttwo.ACTION_PROVIDER_TO_FHIR_JSON" -> {
                var resourceData = intent.getStringExtra("resource")
                var resourceDataClass = intent.getStringExtra("class")

                // Receiving Person Object
                if (resourceDataClass.equals("person")) {
                    var person = gson.fromJson(resourceData, Person::class.java)
                    if (person != null ) {
                        var text = "Json: \n" + resourceData + "\n\n\n" +
                                "JsonToPerson toString(): \n" + person.toString()
                        mIntentTV.setText(text)
                    }

                    // Receiving Encounter Object
                } else if (resourceDataClass.equals("encounter")) {
                    var encounter = gson.fromJson(resourceData, Encounter::class.java)
                    if (encounter != null) {
                        var text = "Json: \n" + resourceData + "\n\n\n" +
                                "JsonToEncounter toString(): \n" + encounter.toString()
                        mIntentTV.setText(text)
                    }
                }
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }
    }


    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
            mIntentTV.setText(it)
        }
    }
}
