package co.edu.udea.udeacov.activities

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import co.edu.udea.udeacov.R

class TablaDinamica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabla_dinamica)
    }

    class TablaDinamica(tableLayout:TableLayout, context:Context) {
        private val context:Context
        private val tableLayout:TableLayout
        private lateinit var header:Array<String>
        private lateinit var data:ArrayList<Array<String>>
        private lateinit var tableRow:TableRow
        private lateinit var txtCell:TextView
        private var indexC:Int = 0
        private var indexR:Int = 0
        init{
            this.tableLayout = tableLayout
            this.context = context
        }
        fun addHeader(header:Array<String>) {
            this.header = header
        }
        fun addData(data:ArrayList<Array<String>>) {
            this.data = data
        }
        private fun newRow() {
            tableRow = TableRow(context)
        }
        private fun newCell() {
            txtCell = TextView(context)
            txtCell.setGravity(Gravity.CENTER)
            txtCell.setTextSize(25F)
        }
        private fun crateHeader() {
            indexC = 0
            newRow()
            while (indexC < header.size)
            {
                newCell()
                txtCell.setText(header[indexC++])
                tableRow.addView(txtCell, newTableRowParams())
            }
            tableLayout.addView(tableRow)
        }
        private fun CreateDataTable() {
            var info:String
            indexR = 1
            while (indexR <= header.size)
            {
                newRow()
                indexC = 0
                while (indexC <= header.size)
                {
                    newCell()
                    val colums = data.get(indexR - 1)
                    info = if ((indexC < colums.size)) colums[indexC] else ""
                    txtCell.setText(info)
                    tableRow.addView(txtCell, newTableRowParams())
                    indexC++
                }
                tableLayout.addView(tableRow)
                indexR++
            }
        }
        private fun newTableRowParams():TableRow.LayoutParams {
            val params = TableRow.LayoutParams()
            params.setMargins(1, 1, 1, 1)
            params.weight = 1F
            return params
        }
    }
}