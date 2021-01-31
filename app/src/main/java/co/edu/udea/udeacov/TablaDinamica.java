package co.edu.udea.udeacov;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;

public class TablaDinamica {
    private Context context;
    private TableLayout tableLayout;
    private String[]header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;
    private boolean multiColor=false;
    int firstColor,secondColor;
    public TablaDinamica(TableLayout tableLayout, Context context) {
        this.tableLayout=tableLayout;
        this.context=context;
    }

    public void addHeader(String[]header){
        this.header=header;
        crateHeader();


    }
    public void addData(ArrayList<String[]> data){
        this.data=data;
        createDataTable();

    }

    private void newRow(){
        tableRow = new TableRow(context);
    }
    private void newCell(){
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(15);
    }
    private void crateHeader(){
        indexC=0;
        newRow();
        while (indexC<header.length){
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }
    private void createDataTable(){
        String info;
        for(indexR=1;indexR<=data.size(); indexR++){
            newRow();
            for(indexC=0;indexC<=header.length; indexC++){
                newCell();
                String[] row = data.get(indexR-1);
                info=(indexC<row.length)?row[indexC]:"";
                txtCell.setText(info);
                tableRow.addView(txtCell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }
    public void addItems(String[] item) {
        String info;
        data.add(item);
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            info = (indexC < item.length) ? item[indexC++] : "";
            txtCell.setText(info);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow, data.size()-1);//Se quito el -1 despues de size para corregir
        reColoring();
        //reColoringLinea();
    }

    private TableRow getRow(int index){
        return (TableRow)tableLayout.getChildAt(index);
    }
    public void backgroundHeader(int color){
        indexC=0;
        while (indexC<header.length){
            txtCell=getCell(0,indexC++);
            txtCell.setBackgroundColor(color);
        }
    }

    public void backgroundData(int firstColor, int secondColor){
        for(indexR=1;indexR<=data.size(); indexR++){
            multiColor=!multiColor;
            for(indexC=0;indexC<=header.length; indexC++){
                txtCell=getCell(indexR,indexC);
                txtCell.setBackgroundColor((multiColor)?firstColor:secondColor);
            }
        }
        this.firstColor=firstColor;
        this.secondColor=secondColor;
    }


    public void reColoring(){
        indexC=0;
        multiColor=!multiColor;
        while (indexC<header.length){
            txtCell=getCell(data.size()-1,indexC++);
            txtCell.setBackgroundColor((multiColor)?firstColor:secondColor);
        }
    }

    private TextView getCell(int rowIndex,int columIndex){
        tableRow=getRow(rowIndex);
        return (TextView)tableRow.getChildAt(columIndex);
    }

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }
}