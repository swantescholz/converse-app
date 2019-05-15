package de.sscholz.converse

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_gol.*

class GolActivity : AppCompatActivity() {

    val golLogic = GolLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gol)
        setSupportActionBar(myToolbar)
        button_next_step.setOnClickListener { _ ->
            golLogic.stepForward()
            updateGridView()
        }
        button_previous_step.setOnClickListener { _ ->
            golLogic.stepBack()
            updateGridView()
        }
        button_resize.setOnClickListener { _ ->
            updateGridView()
        }
        golLogic.readString(GolLogic.Patterns.cross)
        updateGridView()
        myGridView.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
            val coord = golLogic.gridViewPositionToGolCoord(position)
            if (coord in golLogic) {
                (v as ImageView).setImageResource(R.mipmap.dead)
            } else {
                (v as ImageView).setImageResource(R.mipmap.alive)
            }
            golLogic[coord] = !golLogic[coord]
        }
    }

    fun updateGridView() {
        golLogic.recomputeDimensions()
        myGridView.numColumns = golLogic.size.width
        myGridView.adapter = MyAdapter(this, screenWidth, golLogic)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gol_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                golLogic.clear()
                snackbar("Cleared.")
            }
            R.id.action_rotate -> {
                golLogic.rotateClockwise()
                snackbar("Rotated clockwise.")
            }
            R.id.action_cross -> golLogic.readString(GolLogic.Patterns.cross)
            R.id.action_beacon -> golLogic.readString(GolLogic.Patterns.beacon)
            R.id.action_glider -> golLogic.readString(GolLogic.Patterns.glider)
            R.id.action_Spaceship -> golLogic.readString(GolLogic.Patterns.spaceship)
            R.id.action_turtle -> golLogic.readString(GolLogic.Patterns.turtle)
            R.id.action_Acorn -> golLogic.readString(GolLogic.Patterns.acorn)
            else -> return super.onOptionsItemSelected(item)
        }
        updateGridView()
        return true
    }

    class MyAdapter(private val context: Context, val screenWidth: Int, val golLogic: GolLogic) : BaseAdapter() {

        override fun getCount(): Int {
            return golLogic.size.x * golLogic.size.y
        }

        override fun getItem(position: Int): Any {
            return 0
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val imageView: ImageView
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                val size = screenWidth / golLogic.size.width
                imageView = ImageView(context)
                imageView.layoutParams = ViewGroup.LayoutParams(size, size)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setPadding(0, 0, 0, 0)
            } else {
                imageView = convertView as ImageView
            }
            val coord = golLogic.gridViewPositionToGolCoord(position)
            if (coord in golLogic) {
                imageView.setImageResource(R.mipmap.alive)
            } else {
                imageView.setImageResource(R.mipmap.dead)
            }
            return imageView
        }
    }

}
