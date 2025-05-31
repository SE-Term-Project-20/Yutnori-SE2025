package ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.GameController;
import model.*;


public class BoardPanel extends JPanel implements GameListener{
    private BoardLayout layout;
    private Map<BoardNode, Point> coordinates = new HashMap<>();
    private GameController controller;
    private final Map<Rectangle, Piece> clickablePieces = new HashMap<>();
//    private Piece selectedPiece = null;

    public BoardPanel(GameController controller) {
    	this.controller = controller;
    	this.layout = controller.getModel().board().layout(); // controller.board().layout();

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createTitledBorder("Game Board"));
        
        assignCoordinates(layout.type());
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                Point click = e.getPoint();
//                for (Map.Entry<Rectangle, Piece> entry : clickablePieces.entrySet()) {
//                    if (entry.getKey().contains(click)) {
//                        selectedPiece = entry.getValue();
//                    	manager.selectPiece(entry.getValue());  // <- calls GameManager
//                        repaint();
//                        break;
//                    }
//                }
//            }
//        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point click = e.getPoint();
                for (Map.Entry<Rectangle, Piece> entry : clickablePieces.entrySet()) {
                    if (entry.getKey().contains(click)) {
                        controller.onPieceSelected(entry.getValue());
                        repaint();
                        break;
                    }
                }
            }
        });
    }
    
    // lay outer nodes first and then shortcuts and crosses
    private void assignCoordinates(BoardType type) {
        List<BoardNode> allNodes = layout.buildNodes();
        int cx = 250, cy = 250, radius = 150;
        // confirm basis. not sure if coordinates are correct.
        // node connection order redefined in each layout class.
//        Collections.addAll(nodes, start, n00, n01, n02, n03, 
//		           f0, n10, n11, n12, n13, 
//		           f1, n20, n21, n22, n23, 
//		           f2, n30, n31, n32, n33, 
//		           s00, s01, s10, s11, c, s20, s21, s30, s31, entry);

        if (type == BoardType.SQUARE) {
            double[][] grid = { {5,5}, {5,4}, {5,3}, {5,2}, {5,1}, 
            		         {5,0}, {4,0}, {3,0}, {2,0}, {1,0}, 
            		         {0,0}, {0,1}, {0,2}, {0,3}, {0,4}, 
            		         {0,5}, {1,5}, {2,5}, {3,5}, {4,5},
            		         {4.2,0.8}, {3.4,1.6}, {0.8,0.8}, {1.6,1.6}, {2.5,2.5}, {1.6,3.4}, {0.8,4.2}, {3.4,3.4}, {4.2,4.2}  
            };
            
            int i = 0;
            for (BoardNode node : allNodes) {
                if (i >= grid.length) break;
                coordinates.put(node, new Point((int)(60 + 70 * grid[i][0]), (int)(40 + 70 * grid[i][1])));
                i++;
            }
            for (Entry<BoardNode, Point> entry : coordinates.entrySet()) {
            	System.out.println(entry);
            }
        } else if (type == BoardType.PENTAGON) {
        	 int outerSize = 25;      // number of nodes on the outer ring
        	    int shortcut = 5;   // number of shortcut directions
        	    int i = 0;

        	    // 25 outer ring nodes
        	    for (; i < outerSize; i++) {
        	        double angle = - 2 * Math.PI * i / outerSize;
        	        int x = cx + (int)(radius * Math.cos(angle));
        	        int y = cy + (int)(radius * Math.sin(angle));
        	        coordinates.put(allNodes.get(i), new Point(x, y));
        	    }

        	    // 5 shortcut paths with 2 nodes each
        	    for (int j = 0; j < shortcut; j++) {
        	        double baseAngle = - (Math.PI * 2 / shortcut + 2 * Math.PI * j / shortcut);

        	        for (int k = 0; k < 2; k++) {
        	            double step = (k + 1) / 3.0;  // 1/3 and 2/3 of the radius
        	            int x = cx + (int)(radius * (1 - step) * Math.cos(baseAngle));
        	            int y = cy + (int)(radius * (1 - step) * Math.sin(baseAngle));
        	            coordinates.put(allNodes.get(i++), new Point(x, y));
        	        }
        	    }

        	    // 1 center node
        	    coordinates.put(allNodes.get(i), new Point(cx, cy));
        } else {
            int outerSize = 30;
            int shortcut = 6;
            int i = 0;

            // 30 outer ring nodes
            for (; i < outerSize; i++) {
                double angle = - 2 * Math.PI * i / outerSize;
                int x = cx + (int)(radius * Math.cos(angle));
                int y = cy + (int)(radius * Math.sin(angle));
                coordinates.put(allNodes.get(i), new Point(x, y));
            }

            // 6 shortcut paths with 2 nodes each
            for (int j = 0; j < shortcut; j++) {
                double baseAngle = - (Math.PI * 2 / shortcut + 2 * Math.PI * j / shortcut);

                for (int k = 0; k < 2; k++) {
                    double step = (k + 1) / 3.0;  // 1/3 and 2/3 into the radius
                    int x = cx + (int)(radius * (1 - step) * Math.cos(baseAngle));
                    int y = cy + (int)(radius * (1 - step) * Math.sin(baseAngle));
                    coordinates.put(allNodes.get(i++), new Point(x, y));
                }
            }

            // 1 center node
            coordinates.put(allNodes.get(i), new Point(cx, cy));
        }

    }
    // draw a circle (for pentagon/hexagon) for outer nodes only, put shortcuts separately
//	Collections.addAll(nodes, start, n00, n01, n02, n03, 
//	           f0, n10, n11, n12, n13, 
//	           f1, n20, n21, n22, n23, 
//	           f2, n30, n31, n32, n33,
//	           f3, n40, n41, n42, n43,
//	           s00, s01, s10, s11, s20, s21, c, s30, s31, s40, s41, entry);
	
//	ons.addAll(nodes, start, n00, n01, n02, n03, 
//	           f0, n10, n11, n12, n13, 
//	           f1, n20, n21, n22, n23, 
//	           f2, n30, n31, n32, n33,
//	           f3, n40, n41, n42, n43,
//	           f4, n50, n51, n52, n53,
//	       s00, s01, s10, s11, s20, s21, s30, s31, c, s40, s41, s50, s51, entry);
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        for (BoardNode node : coordinates.keySet()) {
            Point p = coordinates.get(node);
            for (BoardNode next : node.nextNodes()) {
                Point q = coordinates.get(next);
                if (q != null) g2d.drawLine(p.x, p.y, q.x, q.y);
            }
        }
        Point p1 = coordinates.get(layout.startNode());
        Point p2 = coordinates.get(layout.entryNode().nextNodes().get(0));

        if (p1 != null && p2 != null) {
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        for (Map.Entry<BoardNode, Point> entry : coordinates.entrySet()) {
            BoardNode node = entry.getKey();
            Point p = entry.getValue();

            if (node == layout.startNode()) {
                g2d.setColor(Color.YELLOW);  // Highlight start node
            } else {
                g2d.setColor(Color.WHITE);
            }

            g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - 10, p.y - 10, 20, 20);
        }

        
        // generate piece components
        clickablePieces.clear();
        int pieceSize = 20;
        int spacing = 10;
        int playerSpacing = 120;  // space between players
        int labelOffset = 15;

        Color[] playerColors = { Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE };

        // Layout players in 2x2 grid
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            Player player = controller.getPlayers().get(i);
            int playerCol = i % 2;
            int playerRow = i / 2;

            int startX = 500 + playerCol * playerSpacing;
            int startY = 60 + playerRow * playerSpacing;

            g2d.setColor(Color.BLACK);
            g2d.drawString(player.id(), startX, startY - labelOffset);

            int pieceCol = 0;
            int pieceRow = 0;

            for (Piece piece : player.reserve()) {
                int x = startX + pieceCol * (pieceSize + spacing);
                int y = startY + pieceRow * (pieceSize + spacing);

                Rectangle r = new Rectangle(x, y, pieceSize, pieceSize);
                clickablePieces.put(r, piece);

                g2d.setColor(playerColors[i % playerColors.length]);
                g2d.fillOval(x, y, pieceSize, pieceSize);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, pieceSize, pieceSize);
                
                if (piece.equals(controller.getSelecetedPiece())) {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawOval(r.x - 2, r.y - 2, r.width + 4, r.height + 4);
                    g2d.setStroke(new BasicStroke(1));
                }

                pieceCol++;
                if (pieceCol >= 2) {
                    pieceCol = 0;
                    pieceRow++;
                }
            }
        }
        
        for (Player player : controller.getPlayers()) {
        	System.out.println("Active pieces for " + player.id() + ": " + player.active().size());
        	for (Piece piece : player.active()) {
        	    BoardNode pos = piece.position();
        	    if (pos == null) continue;

        	    Point p = coordinates.get(pos);
        	    if (p == null) continue;

        	    int x = p.x - 8;
        	    int y = p.y - 8;
        	    int size = 16;

        	    Rectangle r = new Rectangle(x, y, size, size);
        	    clickablePieces.put(r, piece);  

        	    g2d.setColor(getPlayerColor(player));
        	    g2d.fillOval(x, y, size, size);

        	    g2d.setColor(Color.BLACK);
        	    g2d.drawOval(x, y, size, size);

        	    //  highlight if selected
        	    if (piece.equals(controller.getSelecetedPiece())) {
        	        g2d.setColor(Color.RED);
        	        g2d.setStroke(new BasicStroke(2));
        	        g2d.drawOval(x - 2, y - 2, size + 4, size + 4);
        	        g2d.setStroke(new BasicStroke(1));
        	    }
        	}

        }
    }
    
    private Color getPlayerColor(Player p) {
        int index = controller.getPlayers().indexOf(p);
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE};
        return colors[index % colors.length];
    }

    
    @Override
    public void pieceMoved(PieceMovedEvent e) {
        repaint();
    }

    @Override
    public void pieceCaptured(PieceCapturedEvent e) {
        repaint();
    }

    @Override
    public void stackFormed(StackFormedEvent e) {
        repaint();
    }   
}
