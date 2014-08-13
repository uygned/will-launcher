package kindle;

import ixtab.jailbreak.SuicidalKindlet;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.AllPermission;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.amazon.kindle.kindlet.KindletContext;

public class WillLauncher extends SuicidalKindlet implements ActionListener {

	private JLabel label;
	private JButton web;
	private JButton pdf;
	private JButton rss;
	private JButton txt;

	public void onCreate(KindletContext context) {
		super.onCreate(context);

		if (jailbreak.isEnabled())
			jailbreak.getContext().requestPermission(new AllPermission());

		Container pane = context.getRootContainer();
		pane.setLayout(new GridLayout(10, 1, 10, 10));
		// pane.setLayout(new GridBagLayout());

		label = new JLabel(" Applications: ");
		web = new JButton("Will WEB");
		pdf = new JButton("Will PDF");
		txt = new JButton("Will TXT");
		rss = new JButton("Will RSS");
		web.addActionListener(this);
		pdf.addActionListener(this);
		pane.add(label);
		pane.add(web);
		pane.add(pdf);
		pane.add(txt);
		pane.add(rss);
	}

	// private boolean started = false;
	// protected void onStart() {
	// super.onStart();
	//
	// if (started)
	// return;
	//
	// started = true;
	// }

	public void actionPerformed(ActionEvent event) {
		final File workingDir = new File("/mnt/us/extensions/willapps");
		final String script;
		if (event.getSource() == web) {
			script = "./willweb.sh";
		} else if (event.getSource() == pdf) {
			script = "./willpdf.sh";
		} else if (event.getSource() == rss) {
			script = "./willrss.sh";
		} else if (event.getSource() == txt) {
			script = "./willtxt.sh";
		} else {
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (jailbreak.isAvailable() && jailbreak.isEnabled()) {
					try {
						Runtime.getRuntime().exec(
								new String[] { "/bin/sh", script }, null,
								workingDir);
						label.setText(" Starting...");
					} catch (Throwable e) {
						label.setText(e.getMessage());
					}
				} else {
					label.setText(" no jailbreak");
				}
			}
		});
	}
}
