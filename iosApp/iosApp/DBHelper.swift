//
//  DBHelper.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/13.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SQLite3

class DBHelper {
    static let shared = DBHelper()
    
    var db: OpaquePointer?
    var path = "elevator.sqlite"
    
    init() {
        self.db = createDB()
    }
    
    func createDB() -> OpaquePointer? {
        var db: OpaquePointer? = nil
        do {
            let filePath = try FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false).appendingPathComponent(path)
            if sqlite3_open(filePath.path, &db) == SQLITE_OK { return db }
        } catch { print("Error in createDB: \(error.localizedDescription)") }
        print("error in createDB - sqlite3_open")
        return nil
    }
    
    func createTable() {
        // AUTOINCREAMENMT 를 사용하기 위해서는 INTEGER 를 사용해야 한다.
        let query = "CREATE TABLE IF NOT EXISTS elevatorTable (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, number TEXT, build TEXT, date TEXT)"
        var statement: OpaquePointer? = nil
        if sqlite3_prepare_v2(self.db, query, -1, &statement, nil) == SQLITE_OK {
            if sqlite3_step(statement) == SQLITE_DONE {
                print("create table successfully \(String(describing: db))")
            } else {
                let errorMessage = String(cString: sqlite3_errmsg(db))
                print("create talbe step fail: \(errorMessage)")
            }
        } else {
            let errorMessage = String(cString: sqlite3_errmsg(db))
            print("create table sqlite3_prepare fail: \(errorMessage)")
        }
        sqlite3_finalize(statement)
    }
    
    func dropTable() {
        let query = "DROP TABLE elevatorTable"
        var statement: OpaquePointer? = nil
        if sqlite3_prepare_v2(self.db, query, -1, &statement, nil) == SQLITE_OK {
            if sqlite3_step(statement) == SQLITE_DONE {
                print("delete table successfully \(String(describing: db))")
            } else {
                let errorMessage = String(cString: sqlite3_errmsg(db))
                print("delete table step fail: \(errorMessage)")
            }
        } else {
            let errorMessage = String(cString: sqlite3_errmsg(db))
            print("delete table prepare fail: \(errorMessage)")
        }
        sqlite3_finalize(statement)
    }
    
    func insertData(number: String, build: String, date: String) {
        let query = "INSERT INTO elevatorTable(number, build, date) VALUES (?, ?, ?)"
        var statement: OpaquePointer? = nil
        if sqlite3_prepare_v2(self.db, query, -1, &statement, nil) == SQLITE_OK {
            // insert 는 read 와 다르게 컬럼 순서 시작을 1부터 한다.
            sqlite3_bind_text(statement, 1, NSString(string: number).utf8String, -1, nil)
            sqlite3_bind_text(statement, 2, NSString(string: build).utf8String, -1, nil)
            sqlite3_bind_text(statement, 3, NSString(string: date).utf8String, -1, nil)
            if sqlite3_step(statement) == SQLITE_DONE {
                print("insert data successfully: \(String(describing: db))")
            } else {
                let errorMessage = String(cString: sqlite3_errmsg(db))
                print("insert data sqlite3_step fail: \(errorMessage)")
            }
        } else {
            let errorMessage = String(cString: sqlite3_errmsg(db))
            print("insert data prepare fail: \(errorMessage)")
        }
        sqlite3_finalize(statement)
    }
    
    func getHistory() -> [ELEVATOR_ITEM] {
        var result = [ELEVATOR_ITEM]()
        let query = "SELECT number, build, date FROM elevatorTable ORDER BY date DESC LIMIT 50"
        var statement: OpaquePointer? = nil
        if sqlite3_prepare_v2(self.db, query, -1, &statement, nil) == SQLITE_OK {
            while sqlite3_step(statement) == SQLITE_ROW {
                let number = String(cString: sqlite3_column_text(statement, 0))
                let build = String(cString: sqlite3_column_text(statement, 1))
                let date = String(cString: sqlite3_column_text(statement, 2))
                print("getHistory \(number), \(build), \(date)")
                let add_item = ELEVATOR_ITEM(number: number, build: build, date: date)
                result.append(add_item)
            }
        } else {
            let errorMessage = String(cString: sqlite3_errmsg(db))
            print("read data prepare fail: \(errorMessage)")
        }
        sqlite3_finalize(statement)
        return result
    }
}
