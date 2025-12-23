import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ApiService {
  final _storage = const FlutterSecureStorage();
  final String base = const String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'http://localhost:8080/api',
  );

  Future<String?> login(String username, String password) async {
    final res = await http.post(
      Uri.parse('$base/auth/login'),
      body: json.encode({'username': username, 'password': password}),
      headers: {'Content-Type': 'application/json'},
    );
    if (res.statusCode == 200) {
      final m = json.decode(res.body);
      final token = m['token'];
      await _storage.write(key: 'jwt', value: token);
      return token;
    }
    return null;
  }

  Future<List<dynamic>?> getTopics() async {
    final token = await _storage.read(key: 'jwt');
    final res = await http.get(
      Uri.parse('$base/topics'),
      headers: {'Authorization': 'Bearer $token'},
    );
    if (res.statusCode == 200) return json.decode(res.body) as List<dynamic>;
    return null;
  }

  Future<bool> createTopic(String title) async {
    final token = await _storage.read(key: 'jwt');
    final res = await http.post(
      Uri.parse('$base/topics'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
      body: json.encode({'title': title}),
    );
    return res.statusCode == 200;
  }
}
