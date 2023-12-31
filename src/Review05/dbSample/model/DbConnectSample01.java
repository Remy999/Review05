package Review05.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DbConnectSample01 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. DBと接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Sakura361000$"
                );
            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            // 5, 6. Select文の実行と結果を格納／代入

            System.out.print("検索キーワードを入力してください > ");
            String keyword = keyIn();

            String sql = "SELECT * FROM person WHERE id = ?";
            preparedStatement = con.prepareStatement(sql);

            int id = Integer.parseInt(keyword);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();


            // 7. 結果を表示する
            while( rs.next() ){
                // Name列の値を取得
                String name = rs.getString("name");
             // age列の値を取得 　← 追記
                int age = rs.getInt("age");  // ← 追記
                // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
            }
            } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
        }
      }
    }
  }
    /*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列    // ← 追記
     */
    private static String keyIn() {
        String line = null;
        try (BufferedReader key = new BufferedReader(new InputStreamReader(System.in))) {
            line = key.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;

    }
    }