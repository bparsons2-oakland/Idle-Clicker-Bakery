import javax.swing.*;
import java.awt.*;
    
public class IdleCookieBakery extends JFrame {
    //lots of variables to initialize 
    private long cookies = 999999;
    private int cookiesPerSecond = 0;
    private int BuildCount = 0;
    private int tick = 0;
    private int cookieclickAmt = 1;
    private JLabel Clock;
    private JLabel cookieLabel;
    private JLabel cpsLabel;
    private JButton clickButton;
    private JLabel BuildingsOwned;
    private JButton upgradeButton;
    private int AutoCount;
    private JButton upgrade1Button;
    private int BakeryCount;
    private JButton upgrade2Button;
    private int InfraCount;
    private JButton upgrade3Button;
    private int ClickMultCount = 0;
    private boolean milestone = false;


    public IdleCookieBakery() {
        //big window
        setTitle("Idle Cookie Bakery- click the cookie and get to 1,000,000!");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //UI
        cookieLabel = new JLabel("Cookies: 0");
        cpsLabel = new JLabel("Cookies per second: 0");
        BuildingsOwned = new JLabel("Buildings: 0");
        Clock = new JLabel("Minutes: 0");

        ImageIcon cookieIcon = new ImageIcon(getClass().getResource("/images/Cookie-Idle-export.png"));

        //Buttons and cookie icon
        Image img = cookieIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        cookieIcon = new ImageIcon(img);
        
        clickButton = new JButton(cookieIcon);
        clickButton.setBorderPainted(false);
        clickButton.setContentAreaFilled(false);
        clickButton.setFocusPainted(false);
        clickButton.setOpaque(false);
        
        clickButton.addActionListener(e -> addCookies(cookieclickAmt));

        upgradeButton = new JButton("["+ AutoCount +"]Buy an Auto Clicker  +1 CPS (Cost: 10)");
        upgradeButton.addActionListener(e -> buyUpgrade());

        upgrade1Button = new JButton("["+ BakeryCount +"]Expand Grandma Susie's BakeryTM  +10 CPS (Cost: 100)");
        upgrade1Button.addActionListener(e -> buyUpgrade1());

        upgrade2Button = new JButton("["+ InfraCount +"]Upgrade company infrastructure  +90 CPS per current amount of bakeries[" + BakeryCount + "] (Cost: CPS x20)");
        upgrade2Button.addActionListener(e -> buyUpgrade2());

        upgrade3Button = new JButton("["+ ClickMultCount +"]Upgrade Cookies per Click x10 {MAX:4} (Cost: 100000 - 10000 per Infrastructure Upgrade up to 5[" + InfraCount + "])");
        upgrade3Button.addActionListener(e -> buyUpgrade3());

    //center alignment zone and layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        cookieLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Clock.setAlignmentX(Component.CENTER_ALIGNMENT);
        BuildingsOwned.setAlignmentX(Component.CENTER_ALIGNMENT);
        clickButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgrade1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgrade2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgrade3Button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(30));
        panel.add(cookieLabel);
        panel.add(cpsLabel);
        panel.add(BuildingsOwned);
        panel.add(Clock);
        panel.add(Box.createVerticalStrut(5));
        panel.add(clickButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(upgradeButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(upgrade1Button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(upgrade2Button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(upgrade3Button);
        add(panel);

    //timer with timed features
        Timer timer = new Timer(1000, e -> {
            tick++;
            idleGain();

            if (tick % 60 == 0) {
                updateLabels();
            }
            if (cookies >= 1000000L && !milestone){
                milestone = true;
                JOptionPane.showMessageDialog(this, "Congratulations! You have 1,000,000 Cookies!! You took "+tick+ " seconds.");
            }
    });
        timer.start();
    }

    private void addCookies(int amount) {
        cookies += amount;
        updateLabels();
    }

    private void idleGain() {
        cookies += cookiesPerSecond;
        updateLabels();
    }

    //upgrade shop
    private void buyUpgrade() {
        int cost = 10;
        if (cookies >= cost) {
            cookies -= cost;
            cookiesPerSecond += 1;
            BuildCount += 1;
            AutoCount += 1;
            updateLabels();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough cookies!");
        }
    }
    private void buyUpgrade1() {
        int cost = 100;
        if (cookies >= cost) {
            cookies -= cost;
            cookiesPerSecond += 10;
            BuildCount += 1;
            BakeryCount += 1;
            updateLabels();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough cookies!");
        }
    }
    private void buyUpgrade2() {
        int cost = 0;
        if (cookiesPerSecond > 0){
            cost = (cookiesPerSecond * 20);
        }
        if (cookies >= cost) {
            cookies -= cost;
            cookiesPerSecond += (90 * BakeryCount);
            BuildCount += 1;
            InfraCount += 1;
            updateLabels();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough cookies!");
        }
    }
    private void buyUpgrade3() {
        int cost = Math.max(50000, 100000 - (10000 * InfraCount));
        if (cookies >= cost && ClickMultCount <= 3) {
            cookies -= cost;
            cookieclickAmt = (cookieclickAmt * 10);
            BuildCount += 1;
            ClickMultCount += 1;
            updateLabels();
        } else if (cookies < cost) {
            JOptionPane.showMessageDialog(this, "Not enough cookies to buy this upgrade!");
        } else if (ClickMultCount > 3) {
            JOptionPane.showMessageDialog(this, "You've hit the max number of click upgrades!");
        }
    }

//update labels
    private void updateLabels() {
        cookieLabel.setText("Cookies: " + cookies);
        cpsLabel.setText("Cookies per second: " + cookiesPerSecond);
        Clock.setText("Minutes: " + tick/60);
        BuildingsOwned.setText("Buildings: " + BuildCount);
        upgradeButton.setText("["+ AutoCount +"]Buy an Auto Clicker  +1 CPS (Cost: 10)");
        upgrade1Button.setText("["+ BakeryCount +"]Expand Grandma Susie's BakeryTM  +10 CPS (Cost: 100)");
        upgrade2Button.setText("["+ InfraCount +"]Upgrade company infrastructure  +90 CPS per current amount of bakeries[" + BakeryCount + "] (Cost: CPS x20)");
        upgrade3Button.setText("["+ ClickMultCount +"]Upgrade Cookies per Click x10 {MAX:4} (Cost: 100000 - 10000 per Infrastructure Upgrade up to 5[" + InfraCount + "])");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IdleCookieBakery game = new IdleCookieBakery();
            game.setVisible(true);
        });
    }
}
