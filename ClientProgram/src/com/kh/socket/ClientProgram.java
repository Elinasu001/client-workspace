package com.kh.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientProgram {

	public static void main(String[] args) {
		
		/*
		 * 클라이언트 측 프로그램 작성 절차
		 * 
		 * 1) 요청하고자하는 서버의 IP주소와 Port 번호를 지정
		 * 2) 서버에 연결요청 보내기 => Socket 객체 생성(IP 주소, Port 번호)
		 * 3) 서버와 통신할 수 있는 입/출력 스트림 생성
		 * 4) 보조스트림 달기
		 * 5) 스트림을 통해 데이터 주고받기
		 * 6) 자원반납 끝!
		 */
		// 클라이언트용 프로그램
		Scanner sc = new Scanner(System.in);
		
		// 자원반납을 위한 참조변수 선언 및 초기화
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		
		/*
		 * 1) 요청하고자하는 서버의 IP주소와 포트번호 지정
		 * 요청하고자 하는 IP주소 : 내 PC의 IP주소(서버) == 127.0.0.1
		 * 				    	LoopbackIp		  == localhost
		 * 
		 * 
		 * 요청하고자 하는 Port 번호 : 1024
		 */
		String serverIp = "127.0.0.1";
		int portNumber = 1024; // server 에서 만든 포트번호와 동일하게 하기
		
		/*
		 * 2) 서버에게 연결 요청 보내기 => Socket 객체 생성 시 인자를 전달하면됨
		 * 전달할 인자값은 첫 번째 : 
		 * 		서버의 IP주소 : String, 두 번째 : Port 번호 : int
		 */
		try {
			socket = new Socket(serverIp, portNumber);
			// 연결 실패 시 null
			
			if(socket != null) {
				// 서버와의 연결이 성공했다 ~~~~
				System.out.println("서버와의 연결에 성공했습니다!");
				
				// 3) 입/출력용 스트림 소켓 객체로 받아오기 +
				// 4) 보조스트림 달기
				
				// 입력용
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// 출력용
				pw = new PrintWriter(socket.getOutputStream());
				
				// 5) 데이터 주고받기 
				while(true) {
					// 서버로 데이터 출력하기
					System.out.println("그만 하고 싶으시면 그만하고 싶어요.를 입력해주세요.");
					System.out.println("보내고픈 메시지 > ");
					String sendMessage = sc.nextLine();
					if("그만하고 싶어요.".equals(sendMessage)){
						break;
					}
					pw.println(sendMessage);
					pw.flush(); // 스트림 강제로 내보내기
					
					// 서버로부터 입력받기
					String message = br.readLine();
					System.out.println("띵똥 메시지가 도착했어요 > " + message);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 자원 반납 => 생성의 역순으로
			try {
				if(pw != null) {
					pw.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(br != null) {
					br.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if(sc != null) {
					sc.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
