import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiClient {
  static Future<Dio> getDio() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('jwt_token');

    return Dio(BaseOptions(
      baseUrl: 'http://localhost:8080',
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    ));
  }
}
