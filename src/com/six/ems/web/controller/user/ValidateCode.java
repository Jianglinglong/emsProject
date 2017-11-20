package com.six.ems.web.controller.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.ems.web.controller.base.BaseServlet;


/**
 * Servlet implementation class ValidateCode
 */
public class ValidateCode extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidateCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Color randomColor(int f, int bg) {
		f = f > 255 ? 255 : f;
		bg = bg > 255 ? 255 : bg;
		Random rd = new Random();

		int r = f + rd.nextInt(bg - f);
		int g = f + rd.nextInt(bg - f);
		int b = f + rd.nextInt(bg - f);

		return new Color(r, g, b);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void getValidateCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 去掉网页缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		// 定义图片尺寸
		int width = 100;
		int height = 40;

		// 在内存中创建一张空白图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取画布
		Graphics g = image.getGraphics();
		// 设置图片背景颜色
		g.setColor(new Color(255,255,255));
		// 填充背景色
		g.fillRect(0, 0, width, height);
		// 制作要呈现的验证码
		String lower = "abcdefghijklmnopqrstwuvxyz";
		String upper = "ABCDEFGHIJKLMNOPQRSTWUVXYZ";
		String number = "012345678";
		String codeString = upper + number + lower;
		// 定义接收验证吗的字符串
		String code = "";
		////////////////// 随机获取四个字符 ////////////////
		g.setColor(Color.BLACK);
		g.setFont(new Font("Bradley Hand ITC", Font.BOLD, 30));
		for (int i = 0; i < 4; i++) {
			int index = new Random().nextInt(codeString.length());
			// 每次拿到随机字符写入
			char ch = codeString.charAt(index);
			code += String.valueOf(ch);
			g.drawString(String.valueOf(ch), i * 20 + 10, 27);
		}

		////////////////// 画干扰线 /////////////////////
		/*
		 * for (int i = 0 ; i < 120; i++) { int x = new Random().nextInt(width); int y =
		 * new Random().nextInt(height); int x1 = new Random().nextInt(10)+10; int y1 =
		 * new Random().nextInt(10)+10; // 设置图片背景颜色 g.setColor(randomColor(150, 200));
		 * g.drawLine(x, y, x1+x,y1+y); }
		 */
		Random r = new Random();
		for (int i = 0; i < 8; i++) {
			int x1 = r.nextInt(width);
			int x2 = r.nextInt(width);
			int y1 = r.nextInt(height);
			int y2 = r.nextInt(height);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			g.drawLine(x1, y1, x2, y2);
		}

		// 把验证放到session中
		request.getSession().setAttribute("validateCode", code);
		// 输出
		ImageIO.write(image, "JPEG", response.getOutputStream());

	}


}
