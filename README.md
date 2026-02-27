🌳 Tree Analysis Tool (Analizador de Árboles Generales)
Esta aplicación es una herramienta interactiva desarrollada en Java Swing para la creación, visualización y análisis exhaustivo de estructuras de datos tipo árbol general. Permite realizar operaciones desde consultas básicas de raíz hasta cálculos complejos de ancestros, descendientes y validación de caminos.

🚀 Guía de Inicio Rápido
Para replicar la estructura predefinida y activar todas las funciones de análisis, sigue estos pasos:

En el campo "Ingresa tus datos:", pega la siguiente secuencia:

A,B,C,D,E,F,G,H,I,J,K,L,M,N,P,Q

Presiona el botón Generar.

El árbol se visualizará en el componente ViewArbol y todos los botones de análisis se habilitarán automáticamente.
🛠️ Funcionalidades del Panel de Control
📍 Consultas Básicas
  Generar Árbol: Crea la estructura jerárquica. Limpia el campo de entrada tras la creación y bloquea el botón para evitar duplicados.
  Buscar Raíz: Muestra su valor en la consola.
  Hojas: Lista todos los nodos que no tienen hijos (nodos terminales).
🛣️ Análisis de Caminos 
Obtener Caminos: Ingresa una longitud n > 0 para saber cuántos caminos de ese tamaño exacto existen.
Evaluar Camino: Ingresa una secuencia (ej. H,G,F,B,A,C,I) para verificar si representa un camino válido y continuo en el árbol.
🧬 Relaciones Genealógicas
Ancestros:	Muestra todos los nodos superiores en la jerarquía del nodo consultado
Ancestros Propios: 	Similar a ancestros, excluyendo al nodo mismo.
Descendientes:	Lista todos los nodos que se derivan del nodo ingresado.
Hijos: Muestra únicamente los descendientes directos (nivel inmediato inferior).
↔️ Hermanos y Posición
Todos los Hermanos: Lista todos los nodos que comparten el mismo padre.
Hermano Derecho/Izquierdo: 
  Si ingresas 1 dato: Busca los hermanos en esa dirección.
  Si ingresas 2 datos (A,B): Evalúa si existe una relación de posición específica entre ellos. (ej. A,B ;boton Izq).
📏 Mediciones y Recorridos
Altura: Devuelve la altura del nodo específico o la altura total del árbol si el campo está vacío.
Profundidad: Calcula el nivel de profundidad del nodo ingresado.
Recorridos: Despliega un menú para elegir entre Preorden, Postorden e Inorden, imprimiendo el resultado en la consola.
****[!IMPORTANT]
Al iniciar la aplicación, la mayoría de los botones están deshabilitados.
Debes ingresar la lista de nodos inicial y presionar Generar para desbloquear las herramientas de análisis. 
Todas las respuestas y errores se notifican a través de la consola.
