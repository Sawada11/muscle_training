import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  static Future<bool> login(String email, String password) async {
    final dio = Dio();

    try {
      final response = await dio.post(
        'http://localhost:8080/auth/login',
        data: {
          'email': email,
          'password': password,
        },
      );

      final token = response.data['token'];

      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('jwt_token', token);

      print('✅ トークンを保存しました: $token');
      return true; // ✅ 成功
    } catch (e) {
      print('❌ ログイン失敗: $e');
      return false; // ✅ 失敗
    }
  }
}
