import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Date;
import java.util.ArrayList;
//import java.awt.Font;
public class GUI{


    boolean managerLogIn = false;
    Random generator = new Random();
    JButton returnHome;
    Customer loggedIn;
    Bank bank;
    public GUI(){
        bank = new Bank();
        makeCustomerOrManagerWindow();
        //makeUserInterfaceWindow();
        bank.addManager(new Manager(1111,"Ben", "Stevens", "1234", "abcd"));
        bank.loadFromDB();
        //bank.openBank();




    }

    public void makeLoginWindow(boolean failed){
        JFrame f=new JFrame();//creating instance of JFrame
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JLabel welcome = new JLabel("Welcome to Athansios Bank! Please enter your login info.");
        welcome.setFont(welcome.getFont().deriveFont(32.0f));
        welcome.setBounds(50,25,900,50);
        JButton submit=new JButton("Enter Login Info");//creating instance of JButton
        submit.setBounds(400,400,200, 40);//x axis, y axis, width, height
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JLabel unLabel = new JLabel("Username: ");
        JLabel psLabel = new JLabel("Password: ");
        //JCheckBox manager = new JCheckBox("Are you a manager?");
        //manager.setBounds(420,300,175,20);
        username.setBounds(425,200, 150,20);
        unLabel.setBounds(350,200,75,20);
        password.setBounds(425,250, 150,20);
        psLabel.setBounds(350,250,75,20);
        //username.setText("Username");
        //password.setText("Password");
        //f.add(b);//adding button in JFrame
        JLabel error = new JLabel("Incorrect Username or Password, try again");
        error.setBounds(50,500,900,50);
        if(failed){
            f.add(error);
        }



        f.add(welcome);
        f.add(username);
        f.add(unLabel);
        f.add(password);
        f.add(psLabel);
        //f.add(manager);
        f.add(submit);
        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!managerLogIn){
                    loggedIn = bank.getCustomer(username.getText(),password.getText());
                    if(loggedIn==null){
                        makeLoginWindow(true);
                    }else{
                        f.setVisible(false);
                        makeUserInterfaceWindow();
                    }
                }else{
                    if(username.getText().equals(bank.manager.username) && password.getText().equals(bank.manager.password)){
                        f.setVisible(false);
                        makeManagerWindow();
                    }else{
                        makeLoginWindow(true);
                    }
                }
                f.setVisible(false);
                int customerId = generator.nextInt(899999) + 100000;

            }
        });

    }

    public void makeAddOrUpdateStockWindow(String txt){
        JFrame f = new JFrame();
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JLabel title = new JLabel(txt+" a Stock.");
        JTextField name = new JTextField();
        JLabel n = new JLabel("Stock Name");
        JLabel v = new JLabel("Stock Value");
        JTextField val = new JTextField();
        JButton submit = new JButton(txt);
        JButton viewStocks = new JButton("View Current Stocks");

        viewStocks.setBounds(350,20,300,50);
        n.setBounds(250,150,150,50);
        v.setBounds(250,250,150,50);
        title.setBounds(400,50,200,100);
        name.setBounds(400,150,200,50);
        val.setBounds(400,250,200,50);
        submit.setBounds(450,400,100,50);

        f.add(viewStocks);
        f.add(n);
        f.add(v);
        f.add(title);
        f.add(name);
        f.add(val);
        f.add(submit);

        viewStocks.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                popupWindow(bank.returnAllStocks());
            }
        });

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(txt.equals("Update")){
                    bank.changeStockPrice(name.getText(),Double.parseDouble(val.getText()));
                }else{
                    bank.manageAddStock(name.getText(),Double.parseDouble(val.getText()));
                }
                f.setVisible(false);
                makeManagerWindow();
            }
        });

        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public void makeManagerWindow(){
        JFrame f = new JFrame();
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JButton viewCustomers = new JButton("View All Customers");
        JButton viewTransactions = new JButton("View Today's Transactions");
        JButton addStock = new JButton("Add a new Stock Option");
        JButton updateStock = new JButton("Update a Current Stock");
        JButton advanceDay = new JButton("Move a Day Forward");


        viewCustomers.setBounds(250,200,200,100);
        viewTransactions.setBounds(550,200,200,100);
        addStock.setBounds(250,400,200,100);
        updateStock.setBounds(550,400,200,100);
        advanceDay.setBounds(400,550,200,100);

        viewTransactions.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                popupWindow(bank.generateTodaysReport());
            }
        });

        viewCustomers.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                popupWindow(bank.returnAllCustomers());
            }
        });

        addStock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                f.setVisible(false);
                makeAddOrUpdateStockWindow("Add");
            }
        });

        updateStock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                f.setVisible(false);
                makeAddOrUpdateStockWindow("Update");
            }
        });

        advanceDay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                bank.advanceDay();
            }
        });


        f.add(viewCustomers);
        f.add(viewTransactions);
        f.add(addStock);
        f.add(updateStock);
        f.add(advanceDay);
        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public void popupWindow(String txt){
        JFrame f = new JFrame();
        JTextArea t = new JTextArea(txt);
        JScrollPane scroll = new JScrollPane (t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        f.add(scroll);
        t.setBounds(0,0,400,400);
        t.setEditable(true);
        //f.add(t);
        f.setSize(500,500);
        //f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }

    public static void popupWindow(boolean fodder,String txt){
        JFrame f = new JFrame();
        JTextArea t = new JTextArea(txt);
//        JScrollPane scroll = new JScrollPane (t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        f.add(t);
        t.setBounds(0,0,400,400);
        t.setEditable(true);
        //f.add(t);
        f.setSize(500,500);
        //f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }


    public void makeUserInterfaceWindow(){
        JFrame f = new JFrame();
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JButton addAccount    = new JButton("Add Account");
        JButton removeAccount = new JButton("Remove Account");
        JButton withdrawMoney = new JButton("Withdraw Money");
        JButton depositeMoney = new JButton("Deposite Money");
        JButton takeLoan      = new JButton("Take Out Loan");
        JButton buysellStock  = new JButton("Buy Or Sell Stock");
        JButton viewSelf      = new JButton("View Accounts");


        viewSelf.setBounds(400,400,200,50);
        addAccount.setBounds(290,100,200,50);
        removeAccount.setBounds(510,100,200,50);
        withdrawMoney.setBounds(290,200,200,50);
        depositeMoney.setBounds(510,200,200,50);
        takeLoan.setBounds(290,300,200,50);
        buysellStock.setBounds(510, 300, 200, 50);


        viewSelf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                popupWindow(bank.getCustomerInfo(loggedIn));
            }
        });

        addAccount.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeAccountWindow(1,"What kind of Account do you want to make with us?");
            }
        });

        removeAccount.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeAccountWindow(2,"What Account do you want to remove?");
            }
        });

        withdrawMoney.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeAccountWindow(3,"How much money do you want to withdrawal and from which account?");
            }
        });

        depositeMoney.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeAccountWindow(4,"How much money do you want to deposite and to which account?");
            }
        });

        takeLoan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeLoanWindow();
            }
        });

        buysellStock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeBuySellWindow();
            }
        });



        f.add(viewSelf);
        f.add(addAccount);
        f.add(removeAccount);
        f.add(withdrawMoney);
        f.add(depositeMoney);
        f.add(takeLoan);
        if(bank.getSecurityAccount(Integer.toString(loggedIn.getIdNum()))!=null){
            f.add(buysellStock);
        }


        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public void makeAccountWindow(int i, String txt){
        JFrame f = new JFrame();
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JLabel text = new JLabel(txt);
        JTextField money = new JTextField();
        JButton checkingAccount = new JButton("Checking");
        JButton savingAccount = new JButton("Saving");
        JButton securityAccount = new JButton("Security");
        text.setBounds(50,50,900,50);
        money.setBounds(400,150,200,50);
        checkingAccount.setBounds(400,300,200,50);
        savingAccount.setBounds(400,400,200,50);
        securityAccount.setBounds(400,500,200,50);

        checkingAccount.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(i==1){
                    bank.createChecking(Integer.toString(loggedIn.getIdNum()),"0");
                }else if(i==2){
                    bank.removeAccount(Integer.toString(loggedIn.getIdNum()),""+bank.getCheckingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==3){
                    bank.makeWithdrawal((Customer) loggedIn,money.getText(),""+bank.getCheckingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==4){
                    bank.makeDeposit((Customer) loggedIn,money.getText(),""+bank.getCheckingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }

                f.setVisible(false);
                makeUserInterfaceWindow();
            }
        });

        savingAccount.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(i==1){
                    bank.createSaving(Integer.toString(loggedIn.getIdNum()),"0");
                }else if(i==2){
                    bank.removeAccount(Integer.toString(loggedIn.getIdNum()),""+bank.getSavingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==3){
                    bank.makeWithdrawal((Customer) loggedIn,money.getText(),""+bank.getSavingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==4){
                    bank.makeDeposit((Customer) loggedIn,money.getText(),""+bank.getSavingAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }
                f.setVisible(false);
                makeUserInterfaceWindow();
            }
        });

        securityAccount.addActionListener(new ActionListener(){
            boolean trigger = false;
            public void actionPerformed(ActionEvent e){
                if(i==1){
                    trigger = true;
                    bank.createSecurity(loggedIn,bank.getSavingAccount(Integer.toString(loggedIn.getIdNum())),"1000");

                }else if(i==2){
                    bank.removeAccount(Integer.toString(loggedIn.getIdNum()),""+bank.getSecurityAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==3){
                    bank.makeWithdrawal((Customer) loggedIn,money.getText(),""+bank.getSecurityAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }else if(i==4){
                    bank.makeDeposit((Customer) loggedIn,money.getText(),""+bank.getSecurityAccount(Integer.toString(loggedIn.getIdNum())).getAccountNumber());
                }
                f.setVisible(false);
                makeUserInterfaceWindow();
                if(trigger)
                    GUI.popupWindow(true,"1000 dollars will be deposited into the security account from savings. You may add more later");
            }
        });
        f.add(text);
        if(i==3 || i==4){
            f.add(money);
        }

        if(((i==2 || i==3||i==4)&& bank.getCheckingAccount(""+loggedIn.idNum)!=null)|| ((i==1)&&(bank.getCheckingAccount(""+loggedIn.idNum)==null))){
            f.add(checkingAccount);
        }
        if(((i==2 || i==3||i==4)&& bank.getSavingAccount(""+loggedIn.idNum)!=null)|| ((i==1)&&(bank.getSavingAccount(""+loggedIn.idNum)==null))){
            f.add(savingAccount);
        }
        if(((i==2 || i==3||i==4)&& bank.getSecurityAccount(""+loggedIn.idNum)!=null)|| ((bank.getSavingAccount(""+loggedIn.idNum)!=null && bank.getSavingAccount(""+loggedIn.idNum).getValue()>5000)&&(i==1)&&(bank.getSecurityAccount(""+loggedIn.idNum)==null))){
            f.add(securityAccount);
        }


        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public void makeBuySellWindow(){
        JFrame f = new JFrame();
        JButton back = new JButton("Done");
        back.setBounds(400,600,200,50);
        f.add(back);
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeUserInterfaceWindow();
            }
        });
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        ArrayList<JButton> buys = new ArrayList<JButton>();
        ArrayList<JButton> sells = new ArrayList<JButton>();
        int placement = 200;
        for(Stock s: bank.stocks){
            JButton temp = new JButton("Buy a share of "+s.getName()+" for "+"$"+s.getPrice());
            temp.setBounds(50,placement,400,50);
            f.add(temp);
            placement += 75;
            buys.add(temp);

        }
        placement = 200;
        for(String s: bank.customerStockInfo(loggedIn)){
            JButton temp = new JButton("Sell a share of "+s);
            temp.setBounds(550,placement,400,50);
            f.add(temp);
            placement += 75;
            buys.add(temp);
        }

        for(JButton j: buys){
            j.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    bank.buyStocks(loggedIn,j.getText().split(" ")[4],1);
                }
            });
        }

        for(JButton j: sells){
            j.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    bank.sellStocks(loggedIn,j.getText().split(" ")[4],1);
                }
            });
        }

        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public void makeLoanWindow(){
        JFrame f = new JFrame();
        //JLabel c = new JLabel("What are you putting up for collateral?");
        //JTextField collateral = new JTextField();
        JLabel l = new JLabel("How much will you borrow?");
        JTextField loanAmount = new JTextField();
        JButton submit = new JButton("Submit");
        loanAmount.setBounds(400,325,200,50);
        l.setBounds(150,325,250,50);
        submit.setBounds(400,400,200,50);

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                bank.takeLoan(loggedIn,loanAmount.getText());
                f.setVisible(false);
                makeUserInterfaceWindow();
            }
        });
        f.add(submit);
        f.add(l);
        f.add(loanAmount);
        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }



    public void signupWindow(){
        JFrame f = new JFrame();
        returnHome = new JButton("Home");
        returnHome.setBounds(5,5,100,30);
        f.add(returnHome);
        returnHome.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                loggedIn = null;
                managerLogIn = false;
                makeCustomerOrManagerWindow();
            }
        });
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton submit=new JButton("Enter Login Info");//creating instance of JButton
        submit.setBounds(400,400,200, 40);//x axis, y axis, width, height
        JLabel fnLabel = new JLabel("First Name: ");
        JLabel lnLabel = new JLabel("Last Name: ");
        JLabel unLabel = new JLabel("Username: ");
        JLabel psLabel = new JLabel("Password: ");
        //JCheckBox manager = new JCheckBox("Are you a manager?");
        //manager.setBounds(420,300,175,20);
        username.setBounds(425,200, 150,20);
        password.setBounds(425,250, 150,20);
        firstName.setBounds(425,300,150,20);
        lastName.setBounds(425,350,150,20);

        unLabel.setBounds(325,200,100,20);
        psLabel.setBounds(325,250,100,20);
        fnLabel.setBounds(325,300,100,20);
        lnLabel.setBounds(325,350,100,20);

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                int customerId = generator.nextInt(899999) + 100000;
                bank.createCustomer(password.getText(), username.getText(),firstName.getText(),lastName.getText());
                makeLoginWindow(false);
            }
        });

        f.add(username);
        f.add(unLabel);
        f.add(password);
        f.add(psLabel);

        f.add(firstName);
        f.add(lastName);
        f.add(fnLabel);
        f.add(lnLabel);
        //f.add(manager);
        f.add(submit);
        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible





    }

    public void makeCustomerOrManagerWindow(){
        JFrame f= new JFrame();//creating instance of JFrame
        JButton manager = new JButton("Manager");
        JButton newcustomer = new JButton("New Customer");
        JButton returningcustomer = new JButton("Returning Customer");
        manager.setBounds(200,150,600,200);
        newcustomer.setBounds(510,370,290,200);
        returningcustomer.setBounds(200,370,290,200);

        manager.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                managerLogIn = true;
                makeLoginWindow(false);
            }
        });

        newcustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                signupWindow();
            }
        });

        returningcustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                makeLoginWindow(false);
            }
        });
        f.add(manager);
        f.add(newcustomer);
        f.add(returningcustomer);
        f.setSize(1000,700);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}