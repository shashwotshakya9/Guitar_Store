/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarstore;

/**
 *
 * @author MSI
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class GuitarGUI extends javax.swing.JFrame{
  
    /**
     * Creates new form GuitarGUI
     */
        
    public GuitarGUI() {
        initComponents();
    }
    

    
    
    private void Add_components(){//the method is called when the user press the add button
        /*This method is used to add values to the table */
        String Guitar_Id=jTxt_Id.getText();
        String Name=jTxt_name.getText();
        String Brand=jCombo_brand.getSelectedItem().toString();
        String Type="";
        try{
        if(Guitar_Id.equals("") || Name.equals("")){/* to check empty fields in the program*/
            JOptionPane.showMessageDialog(rootPane,"Please fill all the required fields");
        }
        else{
        
        if (jRb_electric.isSelected()) {
            Type =jRb_electric.getText();
        }
        else if (jRb_acoustic.isSelected()) {
            Type =jRb_acoustic.getText();
        }
        else if (jRb_bass.isSelected()) {
            Type =jRb_bass.getText();
        }else{
        Type =jRb_ukelele.getText();
        }
        int Price=Integer.parseInt(jTxt_price.getText());
        int rowCount=jTable1.getRowCount();
        String a = "";
        for(int i=0;i<rowCount;i++){
        a=(String) jTable1.getValueAt(i, 0).toString();
        if(a.equals(Guitar_Id)){/* to check whether id is already entered or not*/
        break;
        }
        }
        if(a.equals(Guitar_Id)){
        JOptionPane.showMessageDialog(rootPane,"ID Already Taken");
        }
        else{
            if(Price>0){/*to check whether price is set to zero or not */
                DefaultTableModel model=(DefaultTableModel)jTable1.getModel();/* to add to the table and call table in the program*/
        model.addRow(new Object[]{Guitar_Id,Brand,Type,Name,Price});}else{
            JOptionPane.showMessageDialog(rootPane,"Price Can't be Zero");
            }}
        }}
        catch(Exception ex){
            /* to catch the exception acquire through the getting value from price as int*/
            JOptionPane.showMessageDialog(rootPane,"Error! Please enter valid data type");
        }
    
    }
     
        
    
    
    private void Addmenu(){/* this method is set to import data from excel file*/
    String filepath = "F:\\emerging\\CourseworkExcel.csv";
        File file = new File(filepath); /* path of the excel file */
        int d=0;
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Object[] lines = bufferedreader.lines().toArray();
            for (int i = 0; i < lines.length; i++) {
                String[] list = lines[i].toString().replaceAll("\"", "").split(",");
                String c="";
                /* to check the id to add in the table */
                for(int j=0;j<list.length;j++){
                    if (",".equals(list[j])){
                        break;
                    }else{
                    c=c+list[j];
                    break;
                    }
                }
        int rowCount=jTable1.getRowCount();
        String a = "";
        for(int k=0;k<rowCount;k++){
        a=(String) jTable1.getValueAt(k, 0).toString();
        if(a.equals(c)){
        break;
        }
        }
        if(a.equals(c)){
                d+=1;
                continue;
                }else{
                    model.addRow(list);
                }
            }
        if (d!=0){
            JOptionPane.showMessageDialog(null,d + " records were already inserted.");
        }
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"error");
        }}
    private void Export(){/* this method is to export the file from the table to a excel file*/
    try{
        JFileChooser fc = new JFileChooser();/* to choose where to export the excel file*/
        int option = fc.showSaveDialog(GuitarGUI.this);
        if(option == JFileChooser.APPROVE_OPTION){
                    String Filename = fc.getSelectedFile().getName(); 
                    String path = fc.getSelectedFile().getParentFile().getPath();
        int len = Filename.length();
	String file = path + "\\" + Filename + ".csv";
	TableModel model = jTable1.getModel();
	FileWriter excel = new FileWriter(file);
        excel.write("CGuitar_Id,Name,Brand,Type,price");
        excel.write("\n");
	for(int i=0; i< model.getRowCount(); i++) {
		for(int j=0; j < model.getColumnCount(); j++) {
		excel.write(model.getValueAt(i,j).toString()+","); // to write in the excel file
		}
		excel.write("\n");
			}
	excel.close();/* closing of the file*/
	}}catch(IOException e){ JOptionPane.showMessageDialog(null,"Error Occured."); }
    }
    private void Sort(){/* to sort out the prices*/
        LinkedList<Integer> Price = new LinkedList<>();
        //LinkedList<String> Id = new LinkedList();
        int a = jTable1.getRowCount();
        for (int i=0;i<a;i++){
            int prices = Integer.parseInt(jTable1.getValueAt(i,4).toString());
            //String t = (String)jTable1.getValueAt(i,0);
            if (prices != 0) {
                Price.add(prices); /*add prices to the linked list*/
               
            }
        }
        int n = Price.size(); 
        for (int i = 0; i < n-1; i++) /*sort out the prices */
        { 
            int min_index = i; 
            for (int j = i+1; j < n; j++){ 
                if (Price.get(j) < Price.get(min_index)) 
                    min_index = j; 
            int temp = Price.get(min_index); 
            
            Price.set(min_index, Price.get(i)); 
            Price.set(i, temp); 
           }
        } /* to add the returned value to the table*/
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
         int nRow = model.getRowCount();
         int nCol = model.getColumnCount();
         Object[][] Orgi_Table = new Object[nRow][nCol];
         for (int i = 0 ; i < nRow ; i++){
            for (int j = 0 ; j < nCol ; j++){
            Orgi_Table[i][j] = model.getValueAt(i,j);
            }
         }
        model.setNumRows(0);
        for (int i = 0; i < Price.size(); i++) {
            for (int j = 0; j < Orgi_Table.length; j++) {
                int p = Integer.parseInt(Orgi_Table[j][4].toString());
                if (Price.get(i)== p ) {
                 model.addRow(new Object[]{Orgi_Table[j][0],Orgi_Table[j][1],Orgi_Table[j][2],Orgi_Table[j][3],Orgi_Table[j][4]});
                break;
                }
            }
        }
    }
            
        private int binarysearch(int low, int high, int key,LinkedList price){ /* to perform binary search*/
    if (low<=high)
       {
          int mid =(low+high)/2;//first the 
 
          if (Integer.parseInt(price.get(mid).toString())==key)
          {
          return Integer.parseInt(price.get(mid).toString());
          }
          else if(Integer.parseInt(price.get(mid).toString())>key)
                  {
                      return binarysearch(low,mid-1,key,price) ;//using the recursive function
                  }
          else if(Integer.parseInt(price.get(mid).toString())<key)
                  {
                      return binarysearch(mid+1,high,key,price);//using binary search as recursive function
                  }
       }
        return -1; 
    }
        private void priceSearch(){ /*to implement the binary search that we have created above*/
        try{
        LinkedList<Integer> price = new LinkedList<>();
        LinkedList<String> Id = new LinkedList();
        int a = jTable1.getRowCount();
        for (int i=0;i<a;i++){
            int prices = Integer.parseInt(jTable1.getValueAt(i,4).toString());
            String t = (String)jTable1.getValueAt(i,0);
            if (prices != 0) {
                price.add(prices);
                Id.add(t);
            }
        }/* to sort out the id and prices*/
        int n = price.size(); 
        for (int i = 0; i < n-1; i++) 
        { 
            int min_index = i; 
            for (int j = i+1; j < n; j++){ 
                if (price.get(j) < price.get(min_index)) 
                    min_index = j; 
            int temp = price.get(min_index); 
            String temp1=Id.get(min_index);
            price.set(min_index, price.get(i)); 
            price.set(i, temp); 
            Id.set(min_index, Id.get(i)); 
            Id.set(i, temp1);
           }
        }
        /* to implement the Binary Search*/
        int key = Integer.parseInt(jTxt_search.getText());
        int low =0;
        int high = price.size()-1;
        int find = binarysearch(low,high,key,price);
         if (find == -1)
            {
                JOptionPane.showMessageDialog(rootPane, "Search data not found");
            }
         else{
        String tosearch=jTxt_search.getText();
            for (int i=0; i<jTable1.getRowCount();i++)
                {
                    String findval=jTable1.getValueAt(i,4).toString();
                    if(findval.equals(tosearch))
                    {
                        JOptionPane.showMessageDialog(rootPane," Guitar_ID: "+jTable1.getValueAt(i,0).toString()+" \n Brand: "+jTable1.getValueAt(i,1).toString()+"\n Type: "+ jTable1.getValueAt(i,2).toString()+"\n Name: "+ jTable1.getValueAt(i,3).toString() +"\n Price: "+jTable1.getValueAt(i,4).toString());
                        break;
                    }
                }
         }}
        catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please enter the valid price");
        }
    }
        private void typeSearch(){/* this method is to search regarding company*/
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int Row = model.getRowCount();
        int Col = model.getColumnCount();
        Object[][] tableData = new Object[Row][Col];
        for (int i = 0 ; i < Row ; i++){
            for (int j = 0 ; j < Col ; j++){
            tableData[i][j] = model.getValueAt(i,j); /* to add the value acquired through table into a object array*/
            }
         }
        String type1 = jCombo_type1.getSelectedItem().toString();
        int count = 0 ;
        for (int i = 0; i < Row; i++) {
            String Value=jTable1.getValueAt(i,2).toString();
            if (Value.equals(type1) ){
                count++;    
            }
        }
        JOptionPane.showMessageDialog(rootPane,count + " Results were found");
        model.setNumRows(0);
        for (int i = 0; i < tableData.length; i++) {
            String Value= (String) tableData[i][2];
            if (Value.equals(type1) ){
                model.addRow(tableData[i]); /* to add rows where brand matches*/
            }   
        }
    }
           
            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guitarAddingDialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLbl_brand = new javax.swing.JLabel();
        jLbl_type = new javax.swing.JLabel();
        jLbl_name = new javax.swing.JLabel();
        jLbl_price = new javax.swing.JLabel();
        jLbl_heading = new javax.swing.JLabel();
        jCombo_brand = new javax.swing.JComboBox<>();
        jTxt_name = new javax.swing.JTextField();
        jTxt_price = new javax.swing.JTextField();
        jBtn_add1 = new javax.swing.JButton();
        jBtn_back = new javax.swing.JButton();
        jRb_electric = new javax.swing.JRadioButton();
        jRb_acoustic = new javax.swing.JRadioButton();
        jRb_bass = new javax.swing.JRadioButton();
        jRb_ukelele = new javax.swing.JRadioButton();
        jLbl_id = new javax.swing.JLabel();
        jTxt_Id = new javax.swing.JTextField();
        jLbl_bgimg1 = new javax.swing.JLabel();
        btnGrp_type = new javax.swing.ButtonGroup();
        jPanel_bg = new javax.swing.JPanel();
        jPanelHead = new javax.swing.JPanel();
        jLbl_guitarstore = new javax.swing.JLabel();
        jPanelBody = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBtn_add = new javax.swing.JButton();
        jBtn_search = new javax.swing.JButton();
        jTxt_search = new javax.swing.JTextField();
        jBtn_sort = new javax.swing.JButton();
        jBtn_Export = new javax.swing.JButton();
        jCombo_type1 = new javax.swing.JComboBox<>();
        jBtn_searchtype = new javax.swing.JButton();
        jLbl_bgimg = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jPanel2.setBackground(new java.awt.Color(49,49,49,220));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 222, 21), 2));
        jPanel2.setMaximumSize(new java.awt.Dimension(397, 330));
        jPanel2.setMinimumSize(new java.awt.Dimension(397, 330));

        jLbl_brand.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLbl_brand.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_brand.setText("Brand");

        jLbl_type.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLbl_type.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_type.setText("Type");

        jLbl_name.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLbl_name.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_name.setText("Name / Model");

        jLbl_price.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLbl_price.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_price.setText("Price");

        jLbl_heading.setFont(new java.awt.Font("Bauhaus 93", 0, 24)); // NOI18N
        jLbl_heading.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_heading.setText("ADD A GUITAR");

        jCombo_brand.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jCombo_brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gibson", "Fender", "Yamaha", "Mantra", "Cort", "Sahana" }));
        jCombo_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_brandActionPerformed(evt);
            }
        });

        jTxt_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_priceActionPerformed(evt);
            }
        });

        jBtn_add1.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        jBtn_add1.setText("Add");
        jBtn_add1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtn_add1MouseClicked(evt);
            }
        });
        jBtn_add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add1ActionPerformed(evt);
            }
        });

        jBtn_back.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        jBtn_back.setText("Back");
        jBtn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtn_backMouseClicked(evt);
            }
        });
        jBtn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_backActionPerformed(evt);
            }
        });

        btnGrp_type.add(jRb_electric);
        jRb_electric.setFont(new java.awt.Font("Berlin Sans FB", 0, 10)); // NOI18N
        jRb_electric.setForeground(new java.awt.Color(255, 222, 21));
        jRb_electric.setText("Electric");
        jRb_electric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRb_electricActionPerformed(evt);
            }
        });

        btnGrp_type.add(jRb_acoustic);
        jRb_acoustic.setFont(new java.awt.Font("Berlin Sans FB", 0, 10)); // NOI18N
        jRb_acoustic.setForeground(new java.awt.Color(255, 222, 21));
        jRb_acoustic.setText("Acoustic");
        jRb_acoustic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRb_acousticActionPerformed(evt);
            }
        });

        jRb_bass.setBackground(new java.awt.Color(49,49,49,220));
        btnGrp_type.add(jRb_bass);
        jRb_bass.setFont(new java.awt.Font("Berlin Sans FB", 0, 10)); // NOI18N
        jRb_bass.setForeground(new java.awt.Color(255, 222, 21));
        jRb_bass.setText("Bass");

        btnGrp_type.add(jRb_ukelele);
        jRb_ukelele.setFont(new java.awt.Font("Berlin Sans FB", 0, 10)); // NOI18N
        jRb_ukelele.setForeground(new java.awt.Color(255, 222, 21));
        jRb_ukelele.setText("Ukelele");

        jLbl_id.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLbl_id.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_id.setText("ID");

        jTxt_Id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_IdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLbl_heading)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLbl_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLbl_price, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLbl_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxt_price)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTxt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(86, 86, 86))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLbl_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCombo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLbl_type, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRb_acoustic)
                                    .addComponent(jRb_electric)
                                    .addComponent(jRb_bass)
                                    .addComponent(jRb_ukelele))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBtn_add1)
                        .addGap(18, 18, 18)
                        .addComponent(jBtn_back)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl_heading)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_brand)
                    .addComponent(jCombo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_type)
                    .addComponent(jRb_electric))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRb_acoustic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRb_bass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRb_ukelele)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_id)
                    .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxt_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_price))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_back)
                    .addComponent(jBtn_add1))
                .addGap(73, 73, 73))
        );

        jLbl_bgimg1.setIcon(new javax.swing.ImageIcon("E:\\Documents\\2nd Year\\Emerging Programming Platforms and  Technologies\\Guitarstore\\src\\guitarstore\\guitarbg.jpg")); // NOI18N
        jLbl_bgimg1.setText("jLabel2");

        javax.swing.GroupLayout guitarAddingDialogLayout = new javax.swing.GroupLayout(guitarAddingDialog.getContentPane());
        guitarAddingDialog.getContentPane().setLayout(guitarAddingDialogLayout);
        guitarAddingDialogLayout.setHorizontalGroup(
            guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
            .addGroup(guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(guitarAddingDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLbl_bgimg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 417, Short.MAX_VALUE))
        );
        guitarAddingDialogLayout.setVerticalGroup(
            guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
            .addGroup(guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(guitarAddingDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(guitarAddingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLbl_bgimg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 395, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(609, 395));

        jPanel_bg.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel_bg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel_bg.setDoubleBuffered(false);
        jPanel_bg.setLayout(null);

        jPanelHead.setBackground(new java.awt.Color(49,49,49,220));
        jPanelHead.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.yellow, java.awt.Color.orange, java.awt.Color.yellow));
        jPanelHead.setToolTipText("");

        jLbl_guitarstore.setFont(new java.awt.Font("Bauhaus 93", 0, 36)); // NOI18N
        jLbl_guitarstore.setForeground(new java.awt.Color(255, 222, 21));
        jLbl_guitarstore.setText("GUITAR STORE");

        javax.swing.GroupLayout jPanelHeadLayout = new javax.swing.GroupLayout(jPanelHead);
        jPanelHead.setLayout(jPanelHeadLayout);
        jPanelHeadLayout.setHorizontalGroup(
            jPanelHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeadLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLbl_guitarstore)
                .addGap(191, 191, 191))
        );
        jPanelHeadLayout.setVerticalGroup(
            jPanelHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeadLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLbl_guitarstore))
        );

        jPanel_bg.add(jPanelHead);
        jPanelHead.setBounds(12, 12, 585, 70);

        jPanelBody.setBackground(new java.awt.Color(49, 49, 49));
        jPanelBody.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.yellow, java.awt.Color.orange, java.awt.Color.yellow));
        jPanelBody.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Available Guitars:");

        jTable1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Gibson", "Acoustic", "Humming Bird", "150000"},
                {"2", "Fender", "Electric", "Strat", "100000"},
                {"3", "Fender", "Bass", "FA", "80000"},
                {"4", "Mantra", "Ukelele", "Moksha", "5000"},
                {"5", "Sahana", "Electric", "Nyauli", "60000"}
            },
            new String [] {
                "ID", "Brand", "Type", "Name / Model", "Price"
            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(jTable1);

        jBtn_add.setText("Add");
        jBtn_add.setToolTipText("Add a Guitar!");
        jBtn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtn_addMouseClicked(evt);
            }
        });
        jBtn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_addActionPerformed(evt);
            }
        });

        jBtn_search.setText("Search");
        jBtn_search.setToolTipText("Search!");
        jBtn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_searchActionPerformed(evt);
            }
        });

        jTxt_search.setToolTipText("Search your Guitar.....");
        jTxt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_searchActionPerformed(evt);
            }
        });

        jBtn_sort.setText("Sort By price");
        jBtn_sort.setToolTipText("Sort It Out!");
        jBtn_sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_sortActionPerformed(evt);
            }
        });

        jBtn_Export.setText("Export");
        jBtn_Export.setToolTipText("Export it out!");
        jBtn_Export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtn_ExportMouseClicked(evt);
            }
        });
        jBtn_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ExportActionPerformed(evt);
            }
        });

        jCombo_type1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electric", "Acoustic", "Bass", "Ukelele" }));
        jCombo_type1.setToolTipText("Choose Required Type");

        jBtn_searchtype.setText("Search By Type");
        jBtn_searchtype.setToolTipText("Type Search!");
        jBtn_searchtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_searchtypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBodyLayout = new javax.swing.GroupLayout(jPanelBody);
        jPanelBody.setLayout(jPanelBodyLayout);
        jPanelBodyLayout.setHorizontalGroup(
            jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBodyLayout.createSequentialGroup()
                        .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxt_search)
                            .addGroup(jPanelBodyLayout.createSequentialGroup()
                                .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanelBodyLayout.createSequentialGroup()
                                        .addComponent(jBtn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jBtn_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCombo_type1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtn_searchtype, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtn_sort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelBodyLayout.setVerticalGroup(
            jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_search)
                    .addComponent(jBtn_sort))
                .addGap(18, 18, 18)
                .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCombo_type1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtn_searchtype)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_add)
                    .addComponent(jBtn_Export))
                .addContainerGap())
        );

        jPanel_bg.add(jPanelBody);
        jPanelBody.setBounds(12, 100, 585, 330);

        jLbl_bgimg.setIcon(new javax.swing.ImageIcon("E:\\Documents\\2nd Year\\Emerging Programming Platforms and  Technologies\\Guitarstore\\src\\guitarstore\\guitarbg.jpg")); // NOI18N
        jLbl_bgimg.setText("jLabel2");
        jPanel_bg.add(jLbl_bgimg);
        jLbl_bgimg.setBounds(2, 1, 610, 450);

        jMenuBar1.setBackground(new java.awt.Color(49, 49, 49));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuBar1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N

        jMenu1.setText("File");
        jMenu1.setFocusable(false);
        jMenu1.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 12)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jMenuItem1.setText("Open File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");
        jMenu2.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 12)); // NOI18N
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_bg, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_bg, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_addActionPerformed
   //TODO add your handling code here:*/
    }//GEN-LAST:event_jBtn_addActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
       Addmenu();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTxt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_searchActionPerformed

    private void jBtn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_searchActionPerformed
   // TODO add your handling code here:*/
     priceSearch();
    }//GEN-LAST:event_jBtn_searchActionPerformed
    
    private void jBtn_sortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_sortActionPerformed
        // TODO add your handling code here:
        Sort();
    }//GEN-LAST:event_jBtn_sortActionPerformed

    private void jCombo_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_brandActionPerformed
        // TODO add your handling code here:
      

    }//GEN-LAST:event_jCombo_brandActionPerformed

    private void jBtn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_backActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtn_backActionPerformed

    private void jRb_electricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRb_electricActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRb_electricActionPerformed

    private void jRb_acousticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRb_acousticActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRb_acousticActionPerformed

    private void jBtn_add1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtn_add1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jBtn_add1MouseClicked

    private void jBtn_add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_add1ActionPerformed
        // TODO add your handling code here:
        Add_components();
        //system.exit;
    }//GEN-LAST:event_jBtn_add1ActionPerformed

    private void jBtn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtn_addMouseClicked
        // TODO add your handling code here:
        guitarAddingDialog.setLocationRelativeTo(null);
       guitarAddingDialog.pack();
         guitarAddingDialog.setVisible(true);
        
        
    }//GEN-LAST:event_jBtn_addMouseClicked

    private void jBtn_ExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtn_ExportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtn_ExportMouseClicked

    private void jBtn_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ExportActionPerformed
        // TODO add your handling code here:
        Export();
    }//GEN-LAST:event_jBtn_ExportActionPerformed

    private void jTxt_IdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_IdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_IdActionPerformed

    private void jTxt_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_priceActionPerformed

    private void jBtn_searchtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_searchtypeActionPerformed
        // TODO add your handling code here:
        typeSearch();
    }//GEN-LAST:event_jBtn_searchtypeActionPerformed

    private void jBtn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtn_backMouseClicked
        // TODO add your handling code here:
        guitarAddingDialog.dispose();
    }//GEN-LAST:event_jBtn_backMouseClicked

        
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuitarGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuitarGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuitarGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuitarGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuitarGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrp_type;
    private javax.swing.JDialog guitarAddingDialog;
    private javax.swing.JButton jBtn_Export;
    private javax.swing.JButton jBtn_add;
    private javax.swing.JButton jBtn_add1;
    private javax.swing.JButton jBtn_back;
    private javax.swing.JButton jBtn_search;
    private javax.swing.JButton jBtn_searchtype;
    private javax.swing.JButton jBtn_sort;
    private javax.swing.JComboBox<String> jCombo_brand;
    private javax.swing.JComboBox<String> jCombo_type1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLbl_bgimg;
    private javax.swing.JLabel jLbl_bgimg1;
    private javax.swing.JLabel jLbl_brand;
    private javax.swing.JLabel jLbl_guitarstore;
    private javax.swing.JLabel jLbl_heading;
    private javax.swing.JLabel jLbl_id;
    private javax.swing.JLabel jLbl_name;
    private javax.swing.JLabel jLbl_price;
    private javax.swing.JLabel jLbl_type;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelBody;
    private javax.swing.JPanel jPanelHead;
    private javax.swing.JPanel jPanel_bg;
    private javax.swing.JRadioButton jRb_acoustic;
    private javax.swing.JRadioButton jRb_bass;
    private javax.swing.JRadioButton jRb_electric;
    private javax.swing.JRadioButton jRb_ukelele;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTxt_Id;
    private javax.swing.JTextField jTxt_name;
    private javax.swing.JTextField jTxt_price;
    private javax.swing.JTextField jTxt_search;
    // End of variables declaration//GEN-END:variables
}
