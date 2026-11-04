package integrador2.herramientas.BinaryTree;

public class BinaryTree<ELEMENT> {


    //region Binary Tree Node Class

    protected class BTNode<ELEMENT> {

        public ELEMENT item;
        public BTNode<ELEMENT> left;
        public BTNode<ELEMENT> right;

        public BTNode() {
            this(null, null, null);
        }
        public BTNode(ELEMENT item) {
            this(item, null, null);
        }
        public BTNode(ELEMENT item, BTNode<ELEMENT> left, BTNode<ELEMENT> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }

        // Muestra el item (dato) del nodo
        public void Visit() {
            System.out.printf("%s ", this.item.toString());
            System.out.println();
        }
    }

    // Atributos
    protected BTNode<ELEMENT> root;

    // Constructor
    public BinaryTree() {
        this.root = null;
    }

    // Representacion parentizada
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb, this.root);
        return sb.toString();
    }
    protected void toString(StringBuilder sb, BTNode<ELEMENT> root) {
        if (root != null) {
            sb.append(root.item.toString());
            if (root.left != null) {
                sb.append("(");
                toString(sb, root.left);
                if (root.right != null) {
                    sb.append(",");
                    toString(sb, root.right);
                }
                sb.append(")");
            } else {
                if (root.right != null) {
                    sb.append("(,");
                    toString(sb, root.right);
                    sb.append(")");
                }
            }
        }
    }

    // Mostrar inscriptos en orden
    public void InOrder() {
        InOrder(this.root);
    }
    protected void InOrder(BTNode<ELEMENT> root) {
        if (root != null) {
            InOrder(root.left);
            root.Visit();
            InOrder(root.right);
        }
    }
    
    // Mostrar inscriptos en orden inverso
    public void DescendingOrder() {
        DescendingOrder(this.root);
    }
    protected void DescendingOrder(BTNode<ELEMENT> root) {
        if (root != null) {
            DescendingOrder(root.right);
            root.Visit();
            DescendingOrder(root.left);
        }
    }

    // Consultar cantidad de inscriptos
    public int NodeCount() {
        return NodeCount(this.root);
    }
    protected int NodeCount(BTNode<ELEMENT> root) {
        if (root != null) {
            return 1 + NodeCount(root.left) + NodeCount(root.right);
        }
        return 0;
    }
}