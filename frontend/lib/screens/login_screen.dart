import 'package:flutter/material.dart';
import '../services/api.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _user = TextEditingController();
  final _pass = TextEditingController();
  bool _loading = false;

  @override
  void dispose() {
    _user.dispose();
    _pass.dispose();
    super.dispose();
  }

  void _login() async {
    setState(() {
      _loading = true;
    });
    final api = ApiService();
    final token = await api.login(_user.text.trim(), _pass.text);
    setState(() {
      _loading = false;
    });
    if (token != null)
      Navigator.pushReplacementNamed(context, '/home');
    else
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Login failed')));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _user,
              decoration: const InputDecoration(labelText: 'Username'),
            ),
            TextField(
              controller: _pass,
              decoration: const InputDecoration(labelText: 'Password'),
              obscureText: true,
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _loading ? null : _login,
              child: _loading
                  ? const CircularProgressIndicator()
                  : const Text('Login'),
            ),
          ],
        ),
      ),
    );
  }
}
