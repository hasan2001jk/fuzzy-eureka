package ru.limit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainServlet() {
        // TODO Auto-generated constructor stub
    }
    
    private static Pattern pattern=Pattern.compile("\\d{4}");
//	private static Pattern sec_pat=Pattern.compile("\\d{4}\\S\\d{4}");
	private static Pattern third_pat=Pattern.compile("\\D+\\s\\→");
	private static Pattern fourth_pat=Pattern.compile("\\→\\s\\D+\\b");

	
//	Сравнивание номеров и запись их в правильном порядке
	private static String necessaryInfo(String stringDate) throws Exception {
		Matcher matcher=pattern.matcher(stringDate);
//		Matcher match_sec=sec_pat.matcher(stringDate);
//		if(match_sec.find()) {
//			return match_sec.group();
//		}else
			if(matcher.find()){
			return matcher.group();
		}else {
		throw new Exception("Can't extract date from String!");
		}
	}
	
	
	private static String station_depart(String stringDate) throws Exception {
		Matcher matcher=third_pat.matcher(stringDate);
		if(matcher.find()) {
			return matcher.group();
		}else{
			throw new Exception("Can't extract date from String!");
		}
	}
	
	
	private static String station_arrive(String stringDate) throws Exception {
		Matcher matcher=fourth_pat.matcher(stringDate);
		if(matcher.find()) {
			return matcher.group();
		}else{
			throw new Exception("Can't extract date from String!");
		}
	}
	
	static String deleteCharacters(String str, int from, int to) {
	    return str.substring(0,from)+str.substring(to);
	}

	private static Pattern pit=Pattern.compile("\\d{4}");
	
	private static String necessary(String stringDate) throws Exception {
		Matcher matcher=pit.matcher(stringDate);
		if(matcher.find()) {
			return matcher.group();
		}else {
		throw new Exception("Can't extract date from String!");
		}
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//******************************************************************************************

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return;
		}

//			
			
				//Ссылка на сайт
					String url ="https://poezdato.net/raspisanie-po-stancyi/samara/elektrichki";
					Document doc = Jsoup.connect(url)
		                .userAgent("Chrome/4.0.249.0 Safari/532.5")
		                .referrer("http://www.google.com")
		                .get();
					Element body=doc.body();
					Elements tablesch=body.select("table");
					Elements tbody =tablesch.select("tbody");
					Elements tr =tbody.select("tr");
					Elements td =tr.select("td");
					ArrayList<Integer> nums =new ArrayList<Integer>();//Массив номеров маршрутов
					ArrayList<String> arrival_time=new ArrayList<String>();//Время прибытия
					ArrayList<String> departure_time=new ArrayList<String>();//Время отправления
					ArrayList<String> arrival_station=new ArrayList<String>();//Станция прибытия
					ArrayList<String> departure_station=new ArrayList<String>();//Станция отправления
					int j=0;//итератор для цикла ниже
					
					boolean is_empty=false;
					String DB_URL = "jdbc:postgresql://localhost:5432/practice";
					String User = "user";
					String Pass = "user";
					System.out.println("Testing connection to Postgresql JDBC");
					Connection connection = null;
					try {
						connection = DriverManager.getConnection(DB_URL, User, Pass);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						PreparedStatement smt=connection.prepareStatement("DELETE FROM Route");
						smt.executeUpdate();
//						ResultSet rs=smt.executeQuery();
//						while(rs.next()) {
//							Integer n =rs.getInt("route_num");
//							if(n.equals(null)) {
//								is_empty=true;
//								break;
//							}
//						}
//						rs.close();
//						if(is_empty==false) {
//							smt=connection.prepareStatement("DELETE FROM Route");
//						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
//						Выстраивание списка без пустых строчек
						for(Element i : td) {
							if(td.get(j).text().isEmpty()) {
								j++;
							}else {
							System.out.println(td.get(j).text());
							j++;
							}
						}
//						Выстраивание списка без пустых строчек**
						
						System.out.print(j);
						System.out.println("\n");
						
						//Взятие номера маршрута
						for(Element i:tr) {
							String dateString=i.select("td").text();
							String tm;
							try {
								tm = necessaryInfo(dateString);
								System.out.println(tm);
								nums.add(Integer.parseInt(tm));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						//Взятие номера маршрута**
						
						//Проверка массива nums
						System.out.println("\n");
						for(int i = 0; i< nums.size(); i++) {
							   System.out.println(nums.get(i));
						}
						//Проверка массива nums**
						
						//Добавление в массив время прибытия и отправления
						for(Element i:tr) {
							String dateString=i.select("td>span[class=_time]").text();
//							String tm = vremya(dateString);
							if(i.select("td>span[class=_time]").get(0).text().isEmpty()) {
								if(i.select("td>span[class=_time]").get(1).text().isEmpty()) {
									
								}else {
									departure_time.add(i.select("td>span[class=_time]").get(1).text());
								}
							}else {
								arrival_time.add(i.select("td>span[class=_time]").get(0).text());
								if(i.select("td>span[class=_time]").get(1).text().isEmpty()) {
									
								}else {
									departure_time.add(i.select("td>span[class=_time]").get(1).text());
								}
							}
							System.out.println(dateString);
						}
						//Добавление в массив время прибытия и отправления**
						
//						System.out.println(arrival.get(0));
//						System.out.println(departure.get(1));
						System.out.println("\n");
						//Станция отправления
						for(Element i:tr) {
							String dateString=i.select("td").get(2).text();
							String word;
							try {
								word = station_depart(dateString);
								if(word.contains("→")) {
									int del=word.indexOf("→");
									String slov=deleteCharacters(word, del-1, del+1);
									System.out.println(slov);
									departure_station.add(slov);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						//Станция отправления**
						
						System.out.println("\n");
						//Станция прибытия
						for(Element i:tr) {
							String dateString=i.select("td").get(2).text();
							String word;
							try {
								word = station_arrive(dateString);
								if(word.contains("→")) {
									int del=word.indexOf("→");
									String slov=deleteCharacters(word, del, del+2);
									System.out.println(slov);
									arrival_station.add(slov);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						//Станция прибытия**
						System.out.println("\n");
						System.out.println(departure_station.get(0));
						System.out.println(arrival_station.get(0));
						//Подключение к бд
						
						
						 

						//Внесение данных в бд
						try {
							PreparedStatement statement=connection.prepareStatement("INSERT INTO Route(id_route,route_num,departure_station,arrival_station) VALUES((?),(?),(?),(?))");
							for(int i=0;i<nums.size(); i++) {
								statement.setInt(1, i+1);
								statement.setInt(2, nums.get(i));
								statement.setString(3, departure_station.get(i));
								statement.setString(4, arrival_station.get(i));
								statement.executeUpdate();
								
							}
							
							
							
							
						}catch (SQLException se) {
							se.printStackTrace();
						}
						
						
						// Закрытие бд
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
						//*********************************************************************************************************************************
						
								
							
								ArrayList <String> urls=new ArrayList<String>();//Массив ссылок
								ArrayList <Integer> numns =new ArrayList<Integer>();//Массив номеров маршрутов
								ArrayList <String> station=new ArrayList<String>();//Массив станций
								ArrayList <String> arrival_tiime=new ArrayList<String>();//Время прибытия
								ArrayList <String> parking_time=new ArrayList<String>();//Стоянка
								ArrayList <String> departure_tiime=new ArrayList<String>();//Время отправления	
								ArrayList <String> in_transit=new ArrayList<String>();//В пути
								int iterator=0;//Итератор для главного цикла
								int ita=0;//Итератор для вложеного цикла в главном цикле
								String a ="https://poezdato.net/raspisanie-po-stancyi/samara/elektrichki";
								Document docs = Jsoup.connect(a)
						            .userAgent("Chrome/4.0.249.0 Safari/532.5")
						            .referrer("http://www.google.com")
						            .get();
								Element bodies=docs.body();
								Elements tables=bodies.select("table");
								Elements tibody=tables.select("tbody");
								Elements tir=tibody.select("tr");
								Elements tdd=tir.select("td");
								//Получение ссылок
								for(Element i: tir) {
									Element elem = i.select("td").get(1);
									System.out.println(elem.select("a").attr("href"));
									urls.add(elem.select("a").attr("href"));
									String num;
									try {
										num = necessary(elem.select("a").attr("href"));
										numns.add(Integer.parseInt(num));
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
								//Получение ссылок**
								System.out.println("\n");
								System.out.println("Testing connection to Postgresql JDBC");
								Connection con = null;
								//Подключение к бд
								
								try {

									con = DriverManager.getConnection(DB_URL, User, Pass);
									PreparedStatement snt=con.prepareStatement("DELETE FROM Stations");
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
								
								
								//Main cycle
								for(int i=0; i<urls.size();i++) {
									String key ="https://poezdato.net"+urls.get(i);
									Document document = Jsoup.connect(key)
							            .userAgent("Chrome/4.0.249.0 Safari/532.5")
							            .referrer("http://www.google.com")
							            .get();
									Element bd=document.body();
									Elements table=bd.select("table[class=train_schedule_table stacktable desktop]");
									Elements tbod=table.select("tbody");
									Elements t_r=tbod.select("tr");
									Elements t_d=t_r.select("td");
									//Получаем станции
									for(Element t: t_r) {
										Element elem = t.select("td").get(0);
										System.out.println(elem.text());
										station.add(elem.text());
									}
									//Получаем станции**
									System.out.println("\n");
									//Получаем время прибытия
									for(Element t: t_r) {
										Element elem = t.select("td").get(1);
										System.out.println(elem.text());
										arrival_tiime.add(elem.text());
									}
									//Получаем время прибытия**
									System.out.println("\n");
									//Получаем время стоянки
									for(Element t: t_r) {
										Element elem = t.select("td").get(2);
										System.out.println(elem.text());
										parking_time.add(elem.text());
									}
									//Получаем время стоянки**
									System.out.println("\n");
									
									System.out.println("\n");
									//Получаем время отправления
									
									for(Element t: t_r) {
										Element elem = t.select("td").get(3);
										System.out.println(elem.text());
										departure_tiime.add(elem.text());
									}
									//Получаем время отправления**
									System.out.println("\n");

									System.out.println("\n");
									//Получаем время в пути
									for(Element t: t_r) {
										Element elem = t.select("td").get(4);
										System.out.println(elem.text());
										in_transit.add(elem.text());
									}
									//Получаем время в пути**
									System.out.println("\n");
									
									//Внесение информации из страниц в цикле
									try {
										PreparedStatement statement=con.prepareStatement("INSERT INTO Stations(id_record,id_route,station_name,arrival_time,parking_time,departure_time,in_transit) VALUES((?),(?),(?),(?),(?),(?),(?))");
										for(int w=0;w<station.size(); w++) {
											statement.setInt(1, ita+1);
											statement.setInt(2, numns.get(iterator));
											statement.setString(3, station.get(w));
											statement.setString(4, arrival_tiime.get(w));
											statement.setString(5, parking_time.get(w));
											statement.setString(6, departure_tiime.get(w));
											statement.setString(7, in_transit.get(w));
											statement.executeUpdate();
											ita++;
										}
								
									}catch (SQLException se) {
										se.printStackTrace();
									}
									//Очистка массивов 
									Iterator<String> x =station.iterator();
//									System.out.println(station.get(0));	
									while(x.hasNext()) {
										String b=x.next();
										x.remove();
									}
									
									Iterator<String> x_at=arrival_tiime.iterator();
									
									while(x_at.hasNext()) {
										String b=x_at.next();
										x_at.remove();
									}
									
									Iterator<String> x_pt=parking_time.iterator();
									
									while(x_pt.hasNext()) {
										String b=x_pt.next();
										x_pt.remove();
									}
									
									Iterator<String> x_dt=departure_tiime.iterator();
									
									while(x_dt.hasNext()) {
										String b=x_dt.next();
										x_dt.remove();
									}
									
									Iterator<String> x_it=in_transit.iterator();
									
									
									while(x_it.hasNext()) {
										String b=x_it.next();
										x_it.remove();
									}
									
									
//									System.out.println(station.get(0));

									iterator++;
								}
								
								
								

								try {
									con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							

					

					
		
		
								
								
		
		
		
		
		
		
		
		
		
		
		
	}

}
