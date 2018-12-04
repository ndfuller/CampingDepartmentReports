package CampingDepartment;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * This is a simple GUI that displays queries from a database.  This class holds
 * the database location, login information, and predefined queries. It uses a 
 * single parameter constructor to select which query to run. It creates a new
 * form that includes a descriptive label at the top, a table that displays the
 * query results, and a text box to narrow the search to chosen 
 * keywords/key letters. This search is case sensitive. Click the "Apply Filter"
 * button to narrow the search and the "Reset Filter" button remove the filter.
 * 
 * @author Nicholas Fuller
 * @version 1.0, 12/10/2016
 * 
 */
@SuppressWarnings("serial")
public class DisplayQueryResultsBox extends JFrame
{
/**
 * Database URL.
 */
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/campingdept" +
                                            "?useSSL=false";

/**
 * Password for access to the database.
 */
private static final String PASSWORD = "password";

/**
 * User name for access to the database.
 */
private static final String USERNAME = "root";

/**
 * MANUFACTURERS_QUERY retrieves all data from manufacturers table.
 */
private static final String MANUFACTURERS_QUERY = "Select * from Manufacturers";

/**
 * ITEMS_ON_ORDER_QUERY displays the number of items on order, po number,
 * order date, and item description from each manufacturer
 */
private static final String ITEMS_ON_ORDER_QUERY  = "SELECT pn.ponum, pn.odate, "
        + "pp.mfgid, i.description, pp.qty\n" +
        "from poplaced pp, ponumber pn, inventory i\n" +
        "where pp.PONUM=pn.PONUM\n" +
        "and pp.style=i.style;";

/**
 * COST_PROFIT_QUERY displays the total cost of inventory in stock and its 
 * potential profit from each manufacturer
 */
private static final String COST_PROFIT_QUERY  = "SELECT mfgid, sum(cost) as "
        + "inventoryCost, sum(RETAIL)-sum(cost) as PotentialProfit\n" +
        "from inventory\n" +
        "group by mfgid;";

/**
 * BEST_SELLERS_QUERY looks at the desired inventory levels and displays 
 * the manufacturer, the category, style number, product description,
 * cost, retail price, desired inventory level, and quantity on hand.
 * The results are sorted in descending order.
 */
private static final String BEST_SELLERS_QUERY  = "select m.mfgname, "
        + "c.CATDESC, i.style, i.DESCRIPTION,\n" +
        "i.cost, i.RETAIL, i.MAXWANT, i.ONHAND\n" +
        "from manufacturers m, categories c, inventory i \n" +
        "where m.mfgid=i.mfgid\n" +
        "and c.catid=i.catid\n" +
        "order by MAXWANT desc;";

/**
 * ORDERS_QUERY displays all orders with the order number, manufacturer name,
 * order date, and cost of the order.
 */
private static final String ORDERS_QUERY  = "select m.MFGNAME, pp.PONUM, pn.ODATE, "
        + "sum(pp.qty*i.cost) as totalCost\n" +
        "from manufacturers m join poplaced pp on m.mfgid=pp.mfgid\n" +
        "join ponumber pn on pn.ponum=pp.ponum\n" +
        "join inventory i on i.style=pp.style\n" +
        "group by PONUM;";

/**
 * PROFIT_GOAL_QUERY looks at all of the current inventory in the system from
 * all manufacturers. It calculates the average margin as a percent and gives an
 * estimated sales goal and cost of goods sold so that the department will make
 * $100,000.  It displays the profit goal, and needed sales and cost of goods
 * sold to reach it.
 */
private static final String PROFIT_GOAL_QUERY  = "select concat('$ ', 100000) as"
        + " ProfitNeeded, concat('$ ', round((1-avg(MARGIN))*100000/avg(MARGIN),2)) "
        + "as COGS,\n" +
        "concat('$ ', round(100000/avg(MARGIN),2)) as SalesGoal\n" +
        "from inventory;";

/**
 * TOTAL_INVENTORY_QUERY displays all of the information about all of the
 * inventory in the system.
 */
private static final String TOTAL_INVENTORY_QUERY  = "SELECT * FROM inventory";  
        
/**
 * DEFAULT_QUERY is the default query for the form. The value is set to a 
 * predefined query based on the constructors parameter.
 */
private static String DEFAULT_QUERY;

/**
 * Number of columns set initially for the query text field
 */
private static final int QUERY_COLUMNS = 25;

/**
 * Number of rows set initially for the query text field
 */
private static final int QUERY_ROWS = 3;

/**
 * The model (schema) for the result set.
 */
private ResultSetTableModel tableModel;

/**
 * constant variable for query selection
 */
private static final int MANUFACTURERS_QUERY_NUMBER = 1;

/**
 * constant variable for query selection
 */
private static final int ITEMS_ON_ORDER_QUERY_NUMBER = 2;

/**
 * constant variable for query selection
 */
private static final int COST_PROFIT_QUERY_NUMBER = 3;

/**
 * constant variable for query selection
 */
private static final int BEST_SELLERS_QUERY_NUMBER = 4;

/**
 * constant variable for query selection
 */
private static final int ORDERS_QUERY_NUMBER = 5;

/**
 * constant variable for query selection
 */
private static final int PROFIT_GOAL_QUERY_NUMBER = 6;

/**
 * constant variable for query selection
 */
private static final int TOTAL_INVENTORY_QUERY_NUMBER = 7;

/**
 * set default query number
 */
private int queryNumber = MANUFACTURERS_QUERY_NUMBER;

/**
 * set default label
 */
private String queryLabel = "List of Manufacturers.";

/**
 * set width of form
 */
private static final int FORM_X_VALUE = 800;

/**
 * set height of form
 */
private static final int FORM_Y_VALUE = 300;

/**
 * Constructor:
 *
 * Sets the title for the window by calling the super constructor.
 * 
 * @param queryNumber determines which query to run
 */
public DisplayQueryResultsBox(int queryNumber)
{
	super("Camping Department Query Results");
        this.queryNumber = queryNumber;
}

/**
 * Create a GUI to show the results of an SQL query.
 */
public void start()
{
    /* Create ResultSetTableModel and display database table. */
    try
    {
        /**
        * determine which query to run and set corresponding label value
        */
        switch (queryNumber) 
        {
            case MANUFACTURERS_QUERY_NUMBER:
                DEFAULT_QUERY = MANUFACTURERS_QUERY;
                queryLabel = "List of Manufacturers.";
                break;
            case ITEMS_ON_ORDER_QUERY_NUMBER:
                DEFAULT_QUERY = ITEMS_ON_ORDER_QUERY;
                queryLabel = "List of all Items On Order.";
                break;
            case COST_PROFIT_QUERY_NUMBER:
                DEFAULT_QUERY = COST_PROFIT_QUERY;
                queryLabel = "Cost of inventory on hand and potential "
                        + "profits if sold at full price from each"
                        + " manufacturer.";
                break;
            case BEST_SELLERS_QUERY_NUMBER:
                DEFAULT_QUERY = BEST_SELLERS_QUERY;
                queryLabel = "List of Best Sellers based on the quantity"
                        + " that you want to keep in stock.";
                break;
            case ORDERS_QUERY_NUMBER:
                DEFAULT_QUERY = ORDERS_QUERY;
                queryLabel = "List of orders that have been placed";
                break;
            case PROFIT_GOAL_QUERY_NUMBER:
                DEFAULT_QUERY = PROFIT_GOAL_QUERY;
                queryLabel = "Shows the estimated cost of goods sold and "
                        + "retail value that needs to be sold to make "
                        + "$100,000 based on the average margin.";
                break;
            case TOTAL_INVENTORY_QUERY_NUMBER:
                DEFAULT_QUERY = TOTAL_INVENTORY_QUERY;
                queryLabel = "List of all information for all items in "
                        + "inventory.";
                break;
            default:
                DEFAULT_QUERY = TOTAL_INVENTORY_QUERY;
                queryLabel = "List of all information for all items in "
                        + "inventory.";
                break;
            }
            
	/** Create TableModel for results of default query. Associate the
         * TableModel with a sorter. 
         */
	tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, 
                DEFAULT_QUERY);
	final TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);

	/** Create Box to manage placement of Label in GUI, and place in the 
         * north frame. This label describes the query that you are looking at.
         */
	{
            Box boxNorth = Box.createHorizontalBox();
            add(boxNorth, BorderLayout.NORTH);
                        
            JLabel message = new JLabel(queryLabel);
            boxNorth.add(message);
	}

	/** Create a result table, wrap it in a scroll pane, and place at the
         * center of the frame. The results are shown in a table format, with
         * columns correspondig to each column from the chosen table in the
         * database, and a row for each entry in that table.		   
        */
	{
            /* Create JTable based on the tableModel. */
            JTable resultTable = new JTable(tableModel);
            add(new JScrollPane(resultTable), BorderLayout.CENTER);
            resultTable.setRowSorter(sorter);
	}
        
	/** Create Box to manage placement of filtering label, textfield, and
         * buttons in GUI, and place in the south frame. Add action listeners
         * to the buttons.
         */        
        {
            Box boxSouth = Box.createHorizontalBox();
            add(boxSouth, BorderLayout.SOUTH);

            JLabel filterLabel = new JLabel("Filter:");
            boxSouth.add(filterLabel);

            final JTextField filterText = new JTextField();
            boxSouth.add(filterText);

            JButton filterButton = new JButton("Apply Filter");
            boxSouth.add(filterButton);
                        
            JButton resetButton = new JButton("Reset Filter");
            boxSouth.add(resetButton);
			
            /* Create listener for filterButton. */
            filterButton.addActionListener((ActionEvent) ->
            {
                String text = filterText.getText();
		if( text.isEmpty() )
                    sorter.setRowFilter(null);
		else
                    try
                    {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }
                    catch( PatternSyntaxException ex )
                    {
                        JOptionPane.showMessageDialog(null, "Bad regex pattern",
                                "Bad regex pattern", JOptionPane.ERROR_MESSAGE);
                    }
            });
                        			
                        /* Create listener for filterButton. */
            resetButton.addActionListener((ActionEvent) ->
            {
                filterText.setText("");
                sorter.setRowFilter(null);
            });
        }

    } 
    catch( SQLException sqlException )
    {
	JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE);
	/* Terminate application with error code. */
	tableModel.disconnectFromDatabase();
	System.exit(1);
    }
    SwingUtilities.invokeLater(this::run);
}

/**
 * Run method adds windowListener, sets default close operation, and form size,
 * and makes form visible.  Database is disconnected from if form is closed.
 */
public void run()
{
    /* Ensure database is closed when user quits application. */
    addWindowListener(new WindowAdapter()
    {
        @Override
	public void windowClosed(WindowEvent event)
        {
            tableModel.disconnectFromDatabase();
	}
    });
	
    /** 
     * Dispose of window when user quits application (this overrides the
     * default of HIDE_ON_CLOSE). 
     */
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(FORM_X_VALUE, FORM_Y_VALUE);
    setVisible(true);
}

/**
 * Create and start the GUI.
 */
public static void launch(int queryNumber)
{
	new DisplayQueryResultsBox(queryNumber).start();
}
}