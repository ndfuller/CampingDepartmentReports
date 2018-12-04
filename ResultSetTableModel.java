package CampingDepartment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.AbstractTableModel;

/**
 * This is a simple class that connects to the database and runs a query.
 * 
 * @author Nicholas Fuller
 * @version 1.0, 12/10/2016
 * 
 */
@SuppressWarnings("serial")
public class ResultSetTableModel extends AbstractTableModel
{
    
/**
 * Keep track of database connection status.
 */
private boolean connectedToDatabase = false;

/**
 * Database connection.
 */
private final Connection connection;

/**
 * Metadata (description) of the result set.
 */
private ResultSetMetaData metaData;

/**
 * Number of rows in the result set.
 */
private int numberOfRows;

/**
 * The result set.
 */
private ResultSet resultSet;

/**
 * The statement to be executed.
 */
private final Statement statement;

/**
 * Constructor: initializes resultSet and obtains its meta data object, and
 * determines number of rows.
 *
 * @param url			the URL of the server and database
 * @param username		the username to use for the connection
 * @param password		the password for that user
 * @param query			the SQL query to be executed
 * @throws java.sql.SQLException if any problem with the database
 */
public ResultSetTableModel(String url, String username,
		String password, String query) throws SQLException
{
	/* Connect to database. */
	connection = DriverManager.getConnection(url, username, password);

	/* Create Statement to query database. */
	statement = connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);

	/* Update database connection status. */
	connectedToDatabase = true;

	/* Set query and execute it. */
	setQuery(query);
}

/**
 * Close statement and connection.
 */
@SuppressWarnings("CallToPrintStackTrace")
public void disconnectFromDatabase()
{
	if( connectedToDatabase )
		/* Close Statement and connection. */
		try
		{
			resultSet.close();
			statement.close();
			connection.close();
		} catch( SQLException sqlException )
		{
			sqlException.printStackTrace();
		} finally  // update database connection status
		{
			connectedToDatabase = false;
		}
}

/**
 * Get class that represents column type.
 *
 * @param column		the index of the given column
 * @return				the class representing the column type
 * @throws IllegalStateException if there is no connection to the database
 */
@Override
@SuppressWarnings("CallToPrintStackTrace")
public Class<?> getColumnClass(int column) throws IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	/* Determine Java class of column. */
	try
	{
		String className = metaData.getColumnClassName(column + 1);

		/* Return Class object that represents className. */
		return Class.forName(className);
	} catch( SQLException | ClassNotFoundException exception )
	{
		exception.printStackTrace();
	}

	return Object.class;		// if problems occur above, assume type Object
}

/**
 * Get number of columns in ResultSet.
 *
 * @return		the number of columns
 * @throws IllegalStateException if there is no connection to the database
 */
@Override
@SuppressWarnings("CallToPrintStackTrace")
public int getColumnCount() throws IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	/* Determine number of columns. */
	try
	{
		return metaData.getColumnCount();
	} catch( SQLException sqlException )
	{
		sqlException.printStackTrace();
	}

	return 0;		// if problems occur above, return 0 for number of columns
}

/**
 * Get name of a particular column in ResultSet.
 *
 * @param column		the index of the particular column
 * @return				the name of that column
 * @throws IllegalStateException if there is no connection to the database
 */
@Override
@SuppressWarnings("CallToPrintStackTrace")
public String getColumnName(int column) throws IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	/* Determine column name. */
	try
	{
		return metaData.getColumnName(column + 1);
	} catch( SQLException sqlException )
	{
		sqlException.printStackTrace();
	}

	return "";				// if problems, return empty string for column name
}

/**
 * Set new database query string.
 * <p>
 * This method is final, as it is called from a constructor.
 *
 * @param query		the SQL query that becomes the statement
 * @throws java.sql.SQLException if there is any database problem
 * @throws IllegalStateException if there is no connection to the database
 */
public final void setQuery(String query)
		throws SQLException, IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	/* Specify query and execute it. */
	resultSet = statement.executeQuery(query);

	/* Obtain meta data for ResultSet. */
	metaData = resultSet.getMetaData();

	/* Determine number of rows in ResultSet, */
	resultSet.last();						// move to last row
	numberOfRows = resultSet.getRow();		// get row number

	/* Notify JTable that model has changed. */
	fireTableStructureChanged();
}

/**
 * Get number of rows in ResultSet
 *
 * @return	the number of rows in the ResultSet
 * @throws IllegalStateException if there is no connection to the database
 */
@Override
public int getRowCount()
		throws IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	return numberOfRows;
}

/**
 * Obtain value in particular row and column.
 *
 * @param row		the particular row
 * @param column	the particular column
 * @return			the value in that location
 * @throws IllegalStateException if there is no connection to the database
 */
@Override
@SuppressWarnings("CallToPrintStackTrace")
public Object getValueAt(int row, int column) throws IllegalStateException
{
	/* Ensure database connection is available. */
	if( !connectedToDatabase )
		throw new IllegalStateException("Not Connected to Database");

	/* Obtain a value at specified ResultSet row and column. */
	try
	{
		resultSet.absolute(row + 1);
		return resultSet.getObject(column + 1);
	} catch( SQLException sqlException )
	{
		sqlException.printStackTrace();
	}

	return "";				// if problems, return empty string object
}
}
