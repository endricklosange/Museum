package com.mydigitalschoolendrick.myapplicationmuseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mydigitalschoolendrick.myapplicationmuseum.data.Fields
import com.mydigitalschoolendrick.myapplicationmuseum.data.MuseumInterface
import com.mydigitalschoolendrick.myapplicationmuseum.data.Records
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var allMuseums = ArrayList<Fields>()
    // val titleMuseum = findViewById<TextView>(R.id.textView_museum_title)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customTitle = findViewById<TextView>(R.id.textView_museum_title)
        val customAdress = findViewById<TextView>(R.id.textViewAdress)
        val customMessage = findViewById<TextView>(R.id.textViewMessage)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.culture.gouv.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val museumInterface: MuseumInterface = retrofit.create(MuseumInterface::class.java)
        val call = museumInterface.getMuseums()

        call.enqueue(object: Callback<Records> {
            override fun onResponse(call: Call<Records>, response: Response<Records>) {
                response.body()?.records.let { responseListOfMuseum ->
                    if (responseListOfMuseum != null) {
                        for (museum in responseListOfMuseum){
                            allMuseums.add(museum)
                            customTitle.setText(museum.fields.nomoff)
                            customAdress.setText(museum.fields.adrl1_m)
                            customMessage.setText(museum.fields.hist)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Records>, t: Throwable) {
                Log.e("MainActivityError",t.message.toString())
            }
        })/*
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

    }

    override fun onMapReady(p0: GoogleMap) {
        for (museum in allMuseums){
            p0.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(museum.fields.latitude.toDouble(),
                        museum.fields.longitude.toDouble()
                    )
                    )
                    .title("Marker")
            )
        }
    }


}