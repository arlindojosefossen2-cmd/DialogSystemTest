package br.com.ajf.dialogsystem.dialog;

import java.awt.*;

public interface IDialog
{
	IDialog add(String name,String text);
	IDialog add(String name,String... texts);
	void draw(Graphics2D graphics2d, String name, int xPos, int yPos);
	void setDialog(boolean dialog);
	boolean isDialog();
}