/*******************************************************************************
 * Copyright 2014 Esri
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ******************************************************************************/
package com.esri.wdc.mpkfeatureeditor;

import com.esri.client.local.LayerDetails;
import com.esri.client.local.LocalFeatureService;
import com.esri.client.local.LocalServiceStartCompleteEvent;
import com.esri.client.local.LocalServiceStartCompleteListener;
import com.esri.core.geodatabase.GeodatabaseFeatureServiceTable;
import com.esri.core.map.CallbackListener;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.FeatureLayer;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

public class MPKFeatureEditor extends javax.swing.JFrame {

    private final HashSet<GeodatabaseFeatureServiceTable> tables = new HashSet<>();

    public MPKFeatureEditor() {
        initComponents();

        final LocalFeatureService service = new LocalFeatureService("../TestTable.mpk");
        service.addLocalServiceStartCompleteListener(new LocalServiceStartCompleteListener() {
            @Override
            public void localServiceStartComplete(LocalServiceStartCompleteEvent e) {
                List<LayerDetails> featureLayers = service.getFeatureLayers();
                /**
                 * Loop backward to put first layer at the end of the map's layer
                 * list and hence drawn on top. It's technically a race condition because
                 * featureServiceTable.initialize is an asynchronous call, but since
                 * this is a local service, the layers should initialize in order.
                 * We could write more code to remove the race condition if desired.
                 */
                for (int layerIndex = featureLayers.size() - 1; layerIndex >= 0; layerIndex--) {
                    final LayerDetails details = featureLayers.get(layerIndex);
                    final GeodatabaseFeatureServiceTable featureServiceTable = new GeodatabaseFeatureServiceTable(
                            service.getUrlFeatureService(), details.getId());
                    tables.add(featureServiceTable);
                    featureServiceTable.setSpatialReference(service.getMapServiceInfo().getSpatialReference());

                    featureServiceTable.initialize(new CallbackListener<GeodatabaseFeatureServiceTable.Status>() {
                        @Override
                        public void onError(Throwable e) {
                            JOptionPane.showMessageDialog(map,
                                    e.getLocalizedMessage(), "", JOptionPane.ERROR_MESSAGE);
                        }

                        @Override
                        public void onCallback(GeodatabaseFeatureServiceTable.Status status) {
                            if (GeodatabaseFeatureServiceTable.Status.INITIALIZED == status) {
                                FeatureLayer layer = new FeatureLayer(featureServiceTable);
                                layer.setName(details.getName());
                                System.out.println("adding " + layer.getName());
                                map.getLayers().add(layer);
                            }
                        }
                    });
                }
            }
        });
        service.startAsync();

        map.getLayers().add(0, new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        map = new com.esri.map.JMap();
        editToolsPicker = new com.esri.toolkit.editing.JEditToolsPicker();
        editToolsPicker.setMap(map);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        editToolsPicker.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(editToolsPicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(editToolsPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        for (GeodatabaseFeatureServiceTable table : tables) {
            table.dispose();
        }
        map.dispose();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(MPKFeatureEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MPKFeatureEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MPKFeatureEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MPKFeatureEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MPKFeatureEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.esri.toolkit.editing.JEditToolsPicker editToolsPicker;
    private com.esri.map.JMap map;
    // End of variables declaration//GEN-END:variables
}
