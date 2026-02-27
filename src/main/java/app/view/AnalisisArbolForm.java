package app.view;

import app.arbol.ArbolService;
import app.arbol.NodoGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AnalisisArbolForm {
    public JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JLabel LblSubtitulo;
    private JLabel LblValor;
    private JButton btnGenerar;
    private JTextField txtDatos;
    private JButton btnEvaluarCamino;
    private JButton btnAncestros;
    private JButton btnNcestrosPropios;
    private JButton btnRecorridos;
    private JLabel lblConsola;
    private JButton btnDescendientes;
    private JButton btnBuscarRaiz;
    private JButton btnGetCaminos;
    private JTextArea txtConsola;
    private JTree ViewArbol;
    private JButton btnHojas;
    private JButton btnAltura;
    private JButton btnProfundidad;
    private JButton btnHermanoDer;
    private JButton btnHermanoIzq;
    private JButton btnHermanos;
    private JButton btnHijos;
    private JTextField txtNodo;
    private JTextField txtLongitud;
    private JTextField txtSecuencia;

    private ArbolService service;

    //Constructor
    public AnalisisArbolForm() {
        service = new ArbolService();
        PanelPrincipal.setPreferredSize(new Dimension(700, 700));
        deshabilitarBotones();
        txtConsola.setEditable(false);
        btnGenerar.addActionListener(this::btnGenerarActionPerformed);
        btnEvaluarCamino.addActionListener(this::btnEvaluarCaminoActionPerformed);
        btnAncestros.addActionListener(this::btnAncestrosActionPerformed);
        btnNcestrosPropios.addActionListener(this::btnAncestrosPropiosActionPerformed);
        btnAltura.addActionListener(this::btnAlturaActionPerformed);
        btnProfundidad.addActionListener(this::btnProfundidadActionPerformed);
        btnRecorridos.addActionListener(this::btnRecorridosActionPerformed);
        btnDescendientes.addActionListener(this::btnDescendientesActionPerformed);
        btnBuscarRaiz.addActionListener(this::btnBuscarRaizActionPerformed);
        btnGetCaminos.addActionListener(this::btnGetCaminosActionPerformed);
        btnHojas.addActionListener(this::btnHojasActionPerformed);
        btnHermanoDer.addActionListener(this::btnHermanoDerActionPerformed);
        btnHermanoIzq.addActionListener(this::btnHermanoIzqActionPerformed);
        btnHermanos.addActionListener(this::btnHermanosActionPerformed);
        btnHijos.addActionListener(this::btnHijosActionPerformed);
        limpiarVistaInicial();
    }

    private void deshabilitarBotones() {

        btnBuscarRaiz.setEnabled(false);
        btnGetCaminos.setEnabled(false);
        btnEvaluarCamino.setEnabled(false);
        btnAncestros.setEnabled(false);
        btnNcestrosPropios.setEnabled(false);
        btnDescendientes.setEnabled(false);
        btnHojas.setEnabled(false);
        btnAltura.setEnabled(false);
        btnProfundidad.setEnabled(false);
        btnHermanoDer.setEnabled(false);
        btnHermanoIzq.setEnabled(false);
        btnHermanos.setEnabled(false);
        btnHijos.setEnabled(false);
        btnRecorridos.setEnabled(false);
    }

    private void habilitarBotones() {

        btnBuscarRaiz.setEnabled(true);
        btnGetCaminos.setEnabled(true);
        btnEvaluarCamino.setEnabled(true);
        btnAncestros.setEnabled(true);
        btnNcestrosPropios.setEnabled(true);
        btnDescendientes.setEnabled(true);
        btnHojas.setEnabled(true);
        btnAltura.setEnabled(true);
        btnProfundidad.setEnabled(true);
        btnHermanoDer.setEnabled(true);
        btnHermanoIzq.setEnabled(true);
        btnHermanos.setEnabled(true);
        btnHijos.setEnabled(true);
        btnRecorridos.setEnabled(true);
    }

    private void btnGenerarActionPerformed(ActionEvent evt) {

        String datos = txtDatos.getText().trim();

        if (datos.isEmpty()) {
            log("Debe ingresar los datos del arbol.");
            return;
        }

        String[] valores = datos.split(",");

        if (valores.length < 16) {
            log("Debe ingresar todos los nodos del arbol.");
            return;
        }

        service.reset();

        // Crear estructura exacta

        service.insertar("A", null);

        service.insertar("B", "A");
        service.insertar("C", "A");
        service.insertar("D", "A");
        service.insertar("E", "A");

        service.insertar("F", "B");
        service.insertar("G", "F");
        service.insertar("H", "G");

        service.insertar("I", "C");
        service.insertar("J", "C");
        service.insertar("K", "J");
        service.insertar("L", "K");
        service.insertar("M", "K");

        service.insertar("N", "E");
        service.insertar("P", "N");
        service.insertar("Q", "N");

        log("Arbol generado correctamente.");

        btnGenerar.setEnabled(false);
        txtDatos.setText("");
        txtDatos.setEnabled(false);
        actualizarJTree();
        habilitarBotones();
    }

    private void actualizarJTree() {

        if (service.obtenerNodoRaiz() == null) {
            return;
        }

        DefaultMutableTreeNode raizVisual =
                construirNodo(service.obtenerNodoRaiz());

        DefaultTreeModel modelo = new DefaultTreeModel(raizVisual);

        ViewArbol.setModel(modelo);

        for (int i = 0; i < ViewArbol.getRowCount(); i++) {
            ViewArbol.expandRow(i);
        }
    }

    private DefaultMutableTreeNode construirNodo(NodoGeneral<String> nodo) {

        DefaultMutableTreeNode nuevo =
                new DefaultMutableTreeNode(nodo.getDato());

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            nuevo.add(construirNodo(hijo));
        }

        return nuevo;
    }

    private void btnBuscarRaizActionPerformed(ActionEvent evt) {

        String raiz = service.obtenerRaiz();

        log("Raíz del árbol: " + raiz);

    }

    private void btnGetCaminosActionPerformed(ActionEvent evt) {

        String input = txtLongitud.getText().trim();

        if (input.isEmpty()) {
            log("Debe ingresar una longitud.");
            return;
        }

        try {
            int longitud = Integer.parseInt(input);

            if (longitud < 0) {
                log("La longitud debe ser >= 0.");
                txtLongitud.setText("");
                return;
            }

            int total = service.contarCaminos(longitud);

            log("Caminos de longitud " + longitud + ": " + total);
            txtLongitud.setText("");

        } catch (NumberFormatException e) {
            log("Debe ingresar un numero valido.");
            txtLongitud.setText("");
        }
    }

    private void btnEvaluarCaminoActionPerformed(ActionEvent evt) {

        String input = txtSecuencia.getText().trim();

        if (input.isEmpty()) {
            log("Debe ingresar una secuencia.");
            txtLongitud.setText("");
            return;
        }

        String[] valores = input.split(",");
        List<String> lista = Arrays.asList(valores);

        boolean esCamino = service.esCamino(lista);

        if (esCamino) {
            log("La secuencia " + input + " ES un camino valido.");
            txtSecuencia.setText("");
        } else {
            log("La secuencia " + input + " NO es un camino valido.");
            txtSecuencia.setText("");
        }
    }

    private void btnAncestrosActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        if (nodo.equals(service.obtenerRaiz())) {
            log("La raiz no tiene ancestros.");
            txtNodo.setText("");
            return;
        }

        log("Ancestros de " + nodo + ": " + service.ancestros(nodo));
        txtNodo.setText("");
    }

    private void btnDescendientesActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        log("Descendientes de " + nodo + ": " + service.descendientes(nodo));
        txtNodo.setText("");
    }

    private void btnHojasActionPerformed(ActionEvent evt) {

        log("Hojas: " + service.hojas());

    }

    private void btnAlturaActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Altura del arbol: " + service.alturaArbol());
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        log("Altura de " + nodo + ": " + service.alturaNodo(nodo));
        txtNodo.setText("");
    }

    private void btnProfundidadActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        log("Profundidad de " + nodo + ": " + service.profundidadNodo(nodo));
        txtNodo.setText("");
    }

    private void btnRecorridosActionPerformed(ActionEvent evt) {

        String[] opciones = {"Preorden", "Postorden", "Inorden"};

        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione recorrido",
                "Recorridos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == null) return;

        switch (seleccion) {
            case "Preorden":
                log("Preorden: " + service.preorden());
                break;
            case "Postorden":
                log("Postorden: " + service.postorden());
                break;
            case "Inorden":
                log("Inorden: " + service.inorden());
                break;
            default:
                log("Seleccione recorrido");
                break;
        }
    }

    private void btnAncestrosPropiosActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        if (nodo.equals(service.obtenerRaiz())) {
            log("La raiz no tiene ancestros propios.");
            txtNodo.setText("");
            return;
        }

        log("Ancestros propios de " + nodo + ": " + service.ancestrosPropios(nodo));
        txtNodo.setText("");
    }

    private void btnHijosActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        log("Hijos de " + nodo + ": " + service.hijos(nodo));
        txtNodo.setText("");
    }

    private void btnHermanosActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        if (!service.existeNodo(nodo)) {
            log("El nodo no existe.");
            txtNodo.setText("");
            return;
        }

        log("Hermanos de " + nodo + ": " + service.hermanos(nodo));
        txtNodo.setText("");
    }

    private void btnHermanoDerActionPerformed(ActionEvent evt) {

        String input = txtNodo.getText().trim();

        if (input.isEmpty()) {
            log("Debe ingresar uno o dos nodos separados por coma.");
            txtNodo.setText("");
            return;
        }

        String[] valores = input.split(",");

        if (valores.length == 1) {

            String hermano = service.hermanoDerecho(valores[0].trim());

            if (hermano == null) {
                log(input + " No tiene hermano derecho.");
                txtNodo.setText("");
            } else {
                log("Hermano derecho de " + input + ": " + hermano);
                txtNodo.setText("");
            }

        } else if (valores.length == 2) {

            boolean resultado = service.estaALaDerechaDe(
                    valores[0].trim(),
                    valores[1].trim()
            );

            log(resultado
                    ? valores[1] + " está a la derecha de " + valores[0]
                    : "No están en esa relación.");
            txtNodo.setText("");

        } else {
            log("Formato incorrecto.");
            txtNodo.setText("");
        }
    }

    private void btnHermanoIzqActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            log("Debe ingresar un nodo.");
            txtNodo.setText("");
            return;
        }

        String hermano = service.hermanoIzquierdo(nodo);

        if (hermano == null) {
            log(nodo + " No tiene hermano izquierdo.");
            txtNodo.setText("");
        } else {
            log("Hermano izquierdo de " + nodo + ": " + hermano);
            txtNodo.setText("");
        }
    }

    private void limpiarVistaInicial() {

        service.reset();

        // Modelo vacío
        DefaultMutableTreeNode raizVacia = new DefaultMutableTreeNode("Arbol vacio");
        DefaultTreeModel modelo = new DefaultTreeModel(raizVacia);
        ViewArbol.setModel(modelo);

        // Limpiar campos
        txtDatos.setText("");
        txtNodo.setText("");
        txtLongitud.setText("");
        txtSecuencia.setText("");
        txtConsola.setText("");

        // Estado inicial
        txtDatos.setEnabled(true);
        btnGenerar.setEnabled(true);
        deshabilitarBotones();
    }

    private void log(String mensaje) {
        txtConsola.append(mensaje + "\n");
        txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        PanelPrincipal = new JPanel();
        PanelPrincipal.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(24, 7, new Insets(0, 0, 0, 0), -1, -1));
        LblTitulo = new JLabel();
        LblTitulo.setText("Arbol General");
        PanelPrincipal.add(LblTitulo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LblValor = new JLabel();
        LblValor.setText("Ingresa tus datos:");
        PanelPrincipal.add(LblValor, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LblSubtitulo = new JLabel();
        LblSubtitulo.setText("Representacion VIsual");
        PanelPrincipal.add(LblSubtitulo, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnBuscarRaiz = new JButton();
        btnBuscarRaiz.setText("Buscar Raiz");
        PanelPrincipal.add(btnBuscarRaiz, new com.intellij.uiDesigner.core.GridConstraints(8, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGetCaminos = new JButton();
        btnGetCaminos.setText("Obtener Caminos");
        PanelPrincipal.add(btnGetCaminos, new com.intellij.uiDesigner.core.GridConstraints(9, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEvaluarCamino = new JButton();
        btnEvaluarCamino.setText("Evaluar Camino");
        PanelPrincipal.add(btnEvaluarCamino, new com.intellij.uiDesigner.core.GridConstraints(10, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAncestros = new JButton();
        btnAncestros.setText("Ancestros");
        PanelPrincipal.add(btnAncestros, new com.intellij.uiDesigner.core.GridConstraints(11, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblConsola = new JLabel();
        lblConsola.setText("Consola");
        PanelPrincipal.add(lblConsola, new com.intellij.uiDesigner.core.GridConstraints(22, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnNcestrosPropios = new JButton();
        btnNcestrosPropios.setText("Ancestros Propios");
        PanelPrincipal.add(btnNcestrosPropios, new com.intellij.uiDesigner.core.GridConstraints(12, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDescendientes = new JButton();
        btnDescendientes.setText("Descendientes Propios");
        PanelPrincipal.add(btnDescendientes, new com.intellij.uiDesigner.core.GridConstraints(13, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRecorridos = new JButton();
        btnRecorridos.setText("Recorridos");
        PanelPrincipal.add(btnRecorridos, new com.intellij.uiDesigner.core.GridConstraints(21, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHojas = new JButton();
        btnHojas.setText("Obtener Hojas");
        PanelPrincipal.add(btnHojas, new com.intellij.uiDesigner.core.GridConstraints(14, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAltura = new JButton();
        btnAltura.setText("Calcular Altura");
        PanelPrincipal.add(btnAltura, new com.intellij.uiDesigner.core.GridConstraints(15, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnProfundidad = new JButton();
        btnProfundidad.setText("Calcular Profundidad");
        PanelPrincipal.add(btnProfundidad, new com.intellij.uiDesigner.core.GridConstraints(16, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHermanoDer = new JButton();
        btnHermanoDer.setText("Hermano Dercha");
        PanelPrincipal.add(btnHermanoDer, new com.intellij.uiDesigner.core.GridConstraints(17, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHermanoIzq = new JButton();
        btnHermanoIzq.setText("Hermano Izquierda");
        PanelPrincipal.add(btnHermanoIzq, new com.intellij.uiDesigner.core.GridConstraints(18, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHermanos = new JButton();
        btnHermanos.setText("Todos los Hemanos");
        PanelPrincipal.add(btnHermanos, new com.intellij.uiDesigner.core.GridConstraints(19, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHijos = new JButton();
        btnHijos.setText("Calcular Hijos");
        PanelPrincipal.add(btnHijos, new com.intellij.uiDesigner.core.GridConstraints(20, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDatos = new JTextField();
        PanelPrincipal.add(txtDatos, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Ingresa tu nodo a evaluar: ");
        PanelPrincipal.add(label1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNodo = new JTextField();
        PanelPrincipal.add(txtNodo, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Ingrensa la longitud de caminos a obtener:");
        PanelPrincipal.add(label2, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtLongitud = new JTextField();
        PanelPrincipal.add(txtLongitud, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Ingresa la secuencia a evaluar:");
        PanelPrincipal.add(label3, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSecuencia = new JTextField();
        PanelPrincipal.add(txtSecuencia, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        PanelPrincipal.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 15, 6, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ViewArbol = new JTree();
        scrollPane1.setViewportView(ViewArbol);
        final JScrollPane scrollPane2 = new JScrollPane();
        PanelPrincipal.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(23, 0, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        scrollPane2.setViewportView(txtConsola);
        btnGenerar = new JButton();
        btnGenerar.setText("Generar");
        PanelPrincipal.add(btnGenerar, new com.intellij.uiDesigner.core.GridConstraints(7, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelPrincipal;
    }

}
