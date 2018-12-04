
package CampingDepartment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * This is a simple GUI that displays a label and buttons to click that run 
 * queries from a database. When a button is clicked, a connection to a database
 * is made, a predefined query is run, and a new form opens to display the query.
 * 
 * @author Nicholas Fuller
 * @version 1.0, 12/10/2016
 * 
 */
@SuppressWarnings("serial")
public class DisplayQueryOptions extends JFrame
{
    /**
     * Constructor
     * 
     * Sets the title for the window by calling the super constructor.
     * 
     */
    public DisplayQueryOptions()
    {
        super("Camping Department Querys");
    }
    
    public void start()
    {
        /**
         * create and fill header panel with a label
         */
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("Select a query to run.");
        headerPanel.add(headerLabel);
    
        /**
         * create and fill button panel with buttons
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
    
        JButton manufacturersInfoButton = new JButton("Manufacturers Info");
        buttonPanel.add(manufacturersInfoButton);
                        
        JButton itemsOnOrderButton = new JButton("Items on order");
        buttonPanel.add(itemsOnOrderButton);
                        
        JButton costProfitButton = new JButton("Cost/Profit");
        buttonPanel.add(costProfitButton);
        
        JButton bestSellersButton = new JButton("Best Sellers");
        buttonPanel.add(bestSellersButton);
			
        JButton ordersButton = new JButton("Orders");
        buttonPanel.add(ordersButton);                
                        
        JButton salesGoalButton = new JButton("$100,000 Profit Goal");
        buttonPanel.add(salesGoalButton);

        JLabel spacerLabel = new JLabel("");
        buttonPanel.add(spacerLabel);
        
        JButton totalInventoryButton = new JButton("Total Inventory");
        buttonPanel.add(totalInventoryButton);
    
        /**
         * create and fill footer panel with a label for formating
         */
        JPanel footerPanel = new JPanel();
        JLabel spacerLabel2 = new JLabel("");
        footerPanel.add(spacerLabel2);
    
        /**
         * add panels to form
         */
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        /**
         * create action listeners for each of the buttons
         */
        manufacturersInfoButton.addActionListener((ActionEvent) ->
        {
            DisplayQueryResultsBox.launch(1);	
        });
    
        itemsOnOrderButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(2);	
	});
        costProfitButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(3);	
	});
        bestSellersButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(4);	
	});        
        ordersButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(5);	
	});
        salesGoalButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(6);	
	});
        totalInventoryButton.addActionListener((ActionEvent) ->
	{
            DisplayQueryResultsBox.launch(7);	
	});
        SwingUtilities.invokeLater(this::run);
    }
   
    /**
     * Run method adds windowListener, sets default close operation, and form size,
     * and makes form visible.  Program closes if form is closed.
     */  
    public void run()
    {
        /* Catch the window closing, and ensure database connection is closed. */
	addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent evt)
            {
                dispose();		// dispose of the windows resoures
                System.exit(0);
            }
	});
        
        /** 
         * Dispose of window when user quits application (this overrides the
         * default of HIDE_ON_CLOSE). 
         */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
	setVisible(true);
    }

    /**
     * Create and start the GUI.
     */
    public static void launch()
    {
        new DisplayQueryOptions().start();
    }
}
