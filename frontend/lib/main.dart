import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'screens/login_screen.dart';
import 'screens/home_screen.dart';
import 'services/api.dart';

void main() {
  runApp(const TodoApp());
}

class TodoApp extends StatelessWidget {
  const TodoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [Provider(create: (_) => ApiService())],
      child: MaterialApp(
        title: 'ToDoList',
        theme: ThemeData(primarySwatch: Colors.blue),
        home: const LoginScreen(),
        routes: {'/home': (_) => const HomeScreen()},
      ),
    );
  }
}
