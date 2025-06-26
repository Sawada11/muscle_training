import 'dart:convert';
import 'package:http/http.dart' as http;
import '../utils/token_storage.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080';

  static Future<bool> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'password': password}),
    );

    if (response.statusCode == 200) {
      final token = jsonDecode(response.body)['token'];
      await TokenStorage.saveToken(token);
      return true;
    } else {
      return false;
    }
  }

  static Future<bool> postRecord(Map<String, dynamic> record) async {
    final token = await TokenStorage.getToken();
    if (token == null) return false;

    final response = await http.post(
      Uri.parse('$baseUrl/records'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: jsonEncode(record),
    );

    return response.statusCode == 200;
  }
}
