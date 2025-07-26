package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");
        Category dried = new Category();
        dried.setName("Dried");
        Category fresh = new Category();
        fresh.setName("Fresh");
        Category exotic = new Category();
        exotic.setName("Exotic");
        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category Data Loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstname("Joe");
        customer1.setLastname("Bloggs");

        Customer customer2 = new Customer();
        customer2.setFirstname("Jane");
        customer2.setLastname("Smith");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        System.out.println("Customer Data Loaded = " + customerRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Home Fruits");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Nuts for Nuts Company");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Vendor Data Loaded = " + vendorRepository.count());
    }
}
