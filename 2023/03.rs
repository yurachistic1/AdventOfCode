use std::collections::HashMap;

mod utils;
use utils::{read_input, get_adjacent_8};

fn main() {
    let file_path = "03.in";

    let input = read_input(file_path);

    part_one(&input);
    part_two(&input)
}

fn part_one(input: &Vec<String>) {
    let matrix: Vec<Vec<char>> = input.iter().map(|line| line.chars().collect()).collect();

    let mut total = 0;

    let mut number_as_string = String::new();

    let mut adjacent = false;

    let (rows, cols) = (matrix.len(), matrix[0].len());

    for i in 0..rows {
        for j in 0..cols {
            let c = matrix[i][j];
            if c.is_ascii_digit() {
                number_as_string.push(c);
                if !adjacent {
                    for (x, y) in get_adjacent_8(i, j, rows, cols) {
                        let element = matrix[x][y];
                        if !element.is_ascii_digit() && element != '.' {
                            adjacent = true;
                            break;
                        }
                    }
                }
            } else {
                if adjacent && !number_as_string.is_empty() {
                    let val: i32 = number_as_string.parse().unwrap();
                    total += val;
                }
                number_as_string = String::new();
                adjacent = false;
            }
        }
    }

    println!("part one: {}", total);
}

fn part_two(input: &Vec<String>) {
    let matrix: Vec<Vec<char>> = input.iter().map(|line| line.chars().collect()).collect();

    let mut number_as_string = String::new();

    let mut adjacent_to_gear = false;

    let mut gear_pos : (usize, usize) = (0, 0);

    let mut gears_map : HashMap<(usize, usize), Vec<i32>> = HashMap::new();

    let (rows, cols) = (matrix.len(), matrix[0].len());

    for i in 0..rows {
        for j in 0..cols {
            let c = matrix[i][j];
            if c.is_ascii_digit() {
                number_as_string.push(c);
                if !adjacent_to_gear {
                    for (x, y) in get_adjacent_8(i, j, rows, cols) {
                        let element = matrix[x][y];
                        if element == '*' {
                            adjacent_to_gear = true;
                            gear_pos = (x, y);
                            break;
                        }
                    }
                }
            } else {
                if adjacent_to_gear && !number_as_string.is_empty() {
                    let val: i32 = number_as_string.parse().unwrap();

                    if let Some(val_list) = gears_map.get_mut(&gear_pos) {
                        val_list.push(val);
                    } else {
                        gears_map.insert(gear_pos, vec![val]);
                    }
                    
                }
                number_as_string = String::new();
                adjacent_to_gear = false;
            }
        }
    }

    fn add_gears(acc: i32, (_, list): (&(usize, usize), &Vec<i32>)) -> i32 {
        if list.len() == 2 {
            return acc + list[0] * list[1];
        } else {
            return acc;
        }
    }

    let result = gears_map.iter().fold(0, add_gears);

    println!("part two: {}", result);
}


