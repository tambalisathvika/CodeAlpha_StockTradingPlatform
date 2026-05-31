
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class StockTradingPlatform {

    static ArrayList<Transaction> transactions
            = new ArrayList<>();

    static HashMap<String, Integer> portfolio
            = new HashMap<>();

    static HashMap<String, Double> market
            = new HashMap<>();

    static double balance = 100000;

    static JTextArea marketArea
            = new JTextArea();

    static JTextArea transactionArea
            = new JTextArea();

    static JTextArea portfolioArea
            = new JTextArea();

    static JTextArea analyticsArea
            = new JTextArea();

    static JTextArea watchlistArea
            = new JTextArea();

    static File transactionFile
            = new File("transactions.txt");

    static File portfolioFile
            = new File("portfolio.txt");

    static int transactionCounter = 1000;

    static void initializeMarket() {

        market.put("TCS", 3500.0);
        market.put("Infosys", 1600.0);
        market.put("Reliance", 2800.0);
        market.put("HDFC", 1700.0);
        market.put("Wipro", 550.0);
        market.put("Tata Motors", 900.0);
    }

    static void refreshMarket() {

        marketArea.setText("");

        for (String stock : market.keySet()) {

            marketArea.append(
                    stock + "  ₹"
                    + market.get(stock) + "\n"
            );
        }
    }

    static void updatePortfolio() {

        portfolioArea.setText("");

        portfolioArea.append(
                "Balance : ₹"
                + balance + "\n\n"
        );

        for (String stock
                : portfolio.keySet()) {

            portfolioArea.append(
                    stock + " : "
                    + portfolio.get(stock)
                    + " shares\n"
            );
        }
    }

    static void updateAnalytics() {

        double value = balance;

        for (String stock
                : portfolio.keySet()) {

            value
                    += portfolio.get(stock)
                    * market.get(stock);
        }

        analyticsArea.setText(
                "Initial Balance : ₹100000\n\n"
                + "Current Balance : ₹"
                + balance + "\n\n"
                + "Portfolio Value : ₹"
                + value + "\n\n"
                + "Profit/Loss : ₹"
                + (value - 100000)
        );
    }

    static void updateTransactions() {

        transactionArea.setText("");

        for (Transaction t
                : transactions) {

            transactionArea.append(
                    t.transactionId + " | "
                    + t.type + " | "
                    + t.stockName + " | Qty : "
                    + t.quantity + "\n"
            );
        }
    }

    static void saveTransactions() {

        try {

            PrintWriter writer
                    = new PrintWriter(
                            new FileWriter(
                                    transactionFile));

            for (Transaction t
                    : transactions) {

                writer.println(
                        t.transactionId + ","
                        + t.type + ","
                        + t.stockName + ","
                        + t.quantity
                );
            }

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    static void savePortfolio() {

        try {

            PrintWriter writer
                    = new PrintWriter(
                            new FileWriter(
                                    portfolioFile));

            writer.println(balance);

            for (String stock
                    : portfolio.keySet()) {

                writer.println(
                        stock + ","
                        + portfolio.get(stock)
                );
            }

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    static void loadPortfolio() {

        try {

            if (!portfolioFile.exists()) {
                return;
            }

            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    portfolioFile));

            String line
                    = reader.readLine();

            if (line != null) {
                balance
                        = Double.parseDouble(line);
            }

            while ((line
                    = reader.readLine()) != null) {

                String[] data
                        = line.split(",");

                portfolio.put(
                        data[0],
                        Integer.parseInt(
                                data[1]
                        )
                );
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    static void loadTransactions() {

        try {

            if (!transactionFile.exists()) {
                return;
            }

            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    transactionFile));

            String line;

            while ((line
                    = reader.readLine()) != null) {

                String[] data
                        = line.split(",");

                transactions.add(
                        new Transaction(
                                data[0],
                                data[1],
                                data[2],
                                Integer.parseInt(
                                        data[3]
                                )
                        )
                );
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        initializeMarket();

        loadPortfolio();

        loadTransactions();

        JFrame frame
                = new JFrame(
                        "Stock Trading Platform"
                );

        frame.setSize(1100, 700);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        JLabel title
                = new JLabel(
                        "STOCK TRADING PLATFORM"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JTabbedPane tabs
                = new JTabbedPane();

        JPanel marketPanel
                = new JPanel(
                        new BorderLayout()
                );

        marketArea.setEditable(false);

        JButton updateMarketButton
                = new JButton(
                        "UPDATE MARKET"
                );

        marketPanel.add(
                new JScrollPane(
                        marketArea
                ),
                BorderLayout.CENTER
        );

        marketPanel.add(
                updateMarketButton,
                BorderLayout.SOUTH
        );
        JPanel buyPanel
                = new JPanel(
                        new GridLayout(
                                5, 2, 10, 10
                        )
                );

        JComboBox<String> buyStockBox
                = new JComboBox<>(
                        market.keySet()
                                .toArray(
                                        new String[0]
                                )
                );

        JTextField buyQtyField
                = new JTextField();

        JButton buyButton
                = new JButton("BUY");

        buyPanel.add(
                new JLabel("Stock")
        );

        buyPanel.add(
                buyStockBox
        );

        buyPanel.add(
                new JLabel("Quantity")
        );

        buyPanel.add(
                buyQtyField
        );

        buyPanel.add(
                new JLabel("")
        );

        buyPanel.add(
                buyButton
        );

        JPanel sellPanel
                = new JPanel(
                        new GridLayout(
                                5, 2, 10, 10
                        )
                );

        JComboBox<String> sellStockBox
                = new JComboBox<>(
                        market.keySet()
                                .toArray(
                                        new String[0]
                                )
                );

        JTextField sellQtyField
                = new JTextField();

        JButton sellButton
                = new JButton("SELL");

        sellPanel.add(
                new JLabel("Stock")
        );

        sellPanel.add(
                sellStockBox
        );

        sellPanel.add(
                new JLabel("Quantity")
        );

        sellPanel.add(
                sellQtyField
        );

        sellPanel.add(
                new JLabel("")
        );

        sellPanel.add(
                sellButton
        );

        JPanel portfolioPanel
                = new JPanel(
                        new BorderLayout()
                );

        portfolioArea.setEditable(false);

        portfolioPanel.add(
                new JScrollPane(
                        portfolioArea
                ),
                BorderLayout.CENTER
        );

        JPanel transactionPanel
                = new JPanel(
                        new BorderLayout()
                );

        transactionArea.setEditable(false);

        transactionPanel.add(
                new JScrollPane(
                        transactionArea
                ),
                BorderLayout.CENTER
        );

        JPanel analyticsPanel
                = new JPanel(
                        new BorderLayout()
                );

        analyticsArea.setEditable(false);

        analyticsPanel.add(
                new JScrollPane(
                        analyticsArea
                ),
                BorderLayout.CENTER
        );

        JPanel watchlistPanel
                = new JPanel(
                        new BorderLayout()
                );

        JTextField watchField
                = new JTextField();

        JButton watchButton
                = new JButton(
                        "ADD TO WATCHLIST"
                );

        watchlistArea.setEditable(false);

        watchlistPanel.add(
                watchField,
                BorderLayout.NORTH
        );

        watchlistPanel.add(
                new JScrollPane(
                        watchlistArea
                ),
                BorderLayout.CENTER
        );

        watchlistPanel.add(
                watchButton,
                BorderLayout.SOUTH
        );

        buyButton.addActionListener(e -> {

            try {

                String stock
                        = buyStockBox
                                .getSelectedItem()
                                .toString();

                int qty
                        = Integer.parseInt(
                                buyQtyField
                                        .getText()
                        );

                double cost
                        = market.get(stock)
                        * qty;

                if (cost > balance) {

                    JOptionPane
                            .showMessageDialog(
                                    frame,
                                    "Insufficient Balance"
                            );

                    return;
                }

                balance -= cost;

                portfolio.put(
                        stock,
                        portfolio.getOrDefault(
                                stock,
                                0
                        ) + qty
                );

                Transaction t
                        = new Transaction(
                                "TXN"
                                + transactionCounter++,
                                "BUY",
                                stock,
                                qty
                        );

                transactions.add(t);

                savePortfolio();
                saveTransactions();

                updatePortfolio();
                updateTransactions();
                updateAnalytics();

                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Stock Purchased"
                        );

            } catch (Exception ex) {

                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Invalid Input"
                        );
            }

        });

        sellButton.addActionListener(e -> {

            try {

                String stock
                        = sellStockBox
                                .getSelectedItem()
                                .toString();

                int qty
                        = Integer.parseInt(
                                sellQtyField
                                        .getText()
                        );

                int owned
                        = portfolio
                                .getOrDefault(
                                        stock,
                                        0
                                );

                if (qty > owned) {

                    JOptionPane
                            .showMessageDialog(
                                    frame,
                                    "Not Enough Shares"
                            );

                    return;
                }

                portfolio.put(
                        stock,
                        owned - qty
                );

                balance
                        += market.get(stock)
                        * qty;

                Transaction t
                        = new Transaction(
                                "TXN"
                                + transactionCounter++,
                                "SELL",
                                stock,
                                qty
                        );

                transactions.add(t);

                savePortfolio();
                saveTransactions();

                updatePortfolio();
                updateTransactions();
                updateAnalytics();

                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Stock Sold"
                        );

            } catch (Exception ex) {

                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Invalid Input"
                        );
            }

        });

        updateMarketButton
                .addActionListener(e -> {

                    Random random
                            = new Random();

                    for (String stock
                            : market.keySet()) {

                        double current
                                = market.get(stock);

                        current
                                += random.nextInt(401)
                                - 200;

                        if (current < 100) {
                            current = 100;
                        }

                        market.put(
                                stock,
                                current
                        );
                    }

                    refreshMarket();
                    updateAnalytics();
                });

        watchButton
                .addActionListener(e -> {

                    String stock
                            = watchField.getText();

                    watchlistArea.append(
                            stock + "\n"
                    );

                    watchField.setText("");
                });

        tabs.addTab(
                "Market",
                marketPanel
        );

        tabs.addTab(
                "Buy",
                buyPanel
        );

        tabs.addTab(
                "Sell",
                sellPanel
        );

        tabs.addTab(
                "Portfolio",
                portfolioPanel
        );

        tabs.addTab(
                "Transactions",
                transactionPanel
        );

        tabs.addTab(
                "Analytics",
                analyticsPanel
        );

        tabs.addTab(
                "Watchlist",
                watchlistPanel
        );

        frame.add(
                title,
                BorderLayout.NORTH
        );

        frame.add(
                tabs,
                BorderLayout.CENTER
        );

        refreshMarket();
        updatePortfolio();
        updateTransactions();
        updateAnalytics();

        frame.setVisible(true);
    }
}

class Transaction {

    String transactionId;
    String type;
    String stockName;
    int quantity;

    Transaction(
            String transactionId,
            String type,
            String stockName,
            int quantity
    ) {

        this.transactionId
                = transactionId;

        this.type
                = type;

        this.stockName
                = stockName;

        this.quantity
                = quantity;
    }
}
